/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.carbon.identity.user.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.exception.AuthenticationFailure;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.model.Group;
import org.wso2.carbon.identity.user.core.principal.User;
import org.wso2.carbon.identity.user.core.stores.UserStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * IdentityManager
 */
public class IdentityManager implements PersistenceManager {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

    private IdentityStoreManager identityStoreManager;

    public IdentityManager(IdentityStoreManager identityStoreManager) {
        this.identityStoreManager = identityStoreManager;
    }

    public AuthenticationContext authenticate(String userID, Object credential) throws AuthenticationFailure,
            UserStoreException {

        AuthenticationContext context = new AuthenticationContext();
        UserStore userStore = getUserStoreOfUser(userID);

        if (!userStore.authenticate(userID, credential)) {
            throw new AuthenticationFailure("Invalid user credentials");
        }
        User subject = new User(userID);
        subject.setUserStoreID(userStore.getUserStoreID());
        context.setUser(subject);
        return context;
    }

    public List<User> searchUserFromClaim(String claimAttribute, String claimValue) throws UserStoreException {

        List<User> users = new ArrayList<>();

        // If the user store name is with the claim.
        if (claimValue.contains("/")) {
            String userStoreDomain = claimValue.substring(0, claimValue.indexOf("/"));
            claimValue = claimValue.substring(1, claimValue.indexOf("/"));
            UserStore userStore = identityStoreManager.getUserStore(userStoreDomain);

            if (userStore != null) {
                users.add(userStore.getUser(claimValue));
            }
        } else {

            // Else, search every user store.
            Map<String, UserStore> userStores = identityStoreManager.getUserStores();

            for (Map.Entry pair : userStores.entrySet()) {
                UserStore userStore = (UserStore) pair.getValue();
                List<User> listUsers = userStore.listUsers(claimAttribute, claimValue);

                for (User user : listUsers) {
                    user.setUserStoreID(userStore.getUserStoreID());
                    UserIDManager.storeUserStoreIDOfUser(user.getUserID(), userStore.getUserStoreID());
                    users.add(user);
                }
            }
        }
        return users;
    }

    public List<Group> getGroupsOfUser(String userID) throws UserStoreException {

        UserStore userStore = getUserStoreOfUser(userID);
        if (userStore != null) {
            return userStore.getGroupsOfUser(userID);
        }
        throw new UserStoreException("Could not find user in local user stores");
    }

    public List<Group> getGroup(String attribute, String value) throws UserStoreException {

        List<Group> groupList = new ArrayList<>();
        if (value.indexOf("/") < 0) {
            String userStoreDomain = value.substring(0, value.indexOf("/"));
            value = value.substring(0, value.indexOf("/"));
            UserStore userStore = identityStoreManager.getUserStore(userStoreDomain);
            Group group = userStore.getGroup(value);
            groupList.add(group);
        } else {
            Map<String, UserStore> userStores = identityStoreManager.getUserStores();
            Iterator it = userStores.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                UserStore userStore = (UserStore) pair.getValue();
                Group group = userStore.getGroup(value);
                group.setUserStoreID(userStore.getUserStoreID());
                UserIDManager.storeUserStoreIDOfGroup(group.getGroupID(), userStore.getUserStoreID());
                groupList.add(group);
            }
        }
        return groupList;
    }

    public List<User> getUsersOfGroup(String groupID) throws UserStoreException {

        UserStore userStore = getUserStoreOfUser(groupID);
        if (userStore != null) {
            return userStore.getUsersOfGroup(groupID);
        }
        throw new UserStoreException("Couldn't find user in local user store");
    }


    /**
     * Returns User of the user with given ID,
     *
     * @param userID
     * @return
     * @throws UserStoreException
     */
    public User searchUser(String userID) throws UserStoreException {

        UserStore userStore = getUserStoreFromMapping(userID);
        User user = null;

        if (userStore != null) {
            user = userStore.getUser(userID);
            if (user != null) {
                user.setUserID(userStore.getUserStoreID());
                UserIDManager.storeUserStoreIDOfUser(userID, userStore.getUserStoreID());
                return user;
            }
        }

        user = searchUserInUserStores(userID);
        if (user != null) {
            return user;
        } else {
            throw new UserStoreException("User does not reside in local user stores");
        }
    }

    public Map<String, String> getUserClaimValues(String userID) throws UserStoreException {

        UserStore userStore = getUserStoreOfUser(userID);
        if (userStore != null) {
            return userStore.getUserClaimValues(userID);
        } else {
            throw new UserStoreException("User is not found in local user stores");
        }
    }

    /**
     * Iteratively searches user in all user stores and returns user
     *
     * @param userID ID of the user.
     * @return use User
     * @throws UserStoreException
     */
    private User searchUserInUserStores(String userID) throws UserStoreException {

        HashMap<String, UserStore> userStores = identityStoreManager.getUserStores();
        User user;
        Iterator it = userStores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UserStore userStore = (UserStore) pair.getValue();
            user = userStore.getUser(userID);
            user.setUserStoreID(userStore.getUserStoreID());
            UserIDManager.storeUserStoreIDOfUser(userID, userStore.getUserStoreID());
            return user;
        }
        return null;
    }

    /**
     * To retrieve user store from user ID
     *
     * @param userID
     * @return
     * @throws UserStoreException
     */
    private UserStore getUserStoreFromMapping(String userID) throws UserStoreException {
        String storeID = UserIDManager.getUserStoreIdOfUser(userID);
        if (userID != null) {
            UserStore userStore = identityStoreManager.getUserStoreFromID(storeID);
            if (userStore != null) {
                return userStore;
            }
        }
        return null;
    }

    private UserStore getUserStoreOfUser(String userID) throws UserStoreException {
        UserStore userStore = getUserStoreFromMapping(userID);
        if (userStore == null) {
            User user = searchUserInUserStores(userID);
            userStore = identityStoreManager.getUserStore(user.getUserStoreID());
        }

        if (userStore != null) {
            UserIDManager.storeUserStoreIDOfUser(userID, userStore.getUserStoreID());
            return userStore;
        } else {
            throw new UserStoreException("User couldn't find in the local user stores");
        }
    }

    public User addUser(String userStoreID, Map<String, String> claims, Object credential, List<String>
            groupList, boolean requirePasswordChange) throws UserStoreException {
        if (userStoreID == null) {
            userStoreID = "1";
        }
        UserStore userStore = identityStoreManager.getUserStoreFromID(userStoreID);
        return userStore.addUser(claims, credential, groupList, true);
    }

}
