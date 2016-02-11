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
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.UserStore;

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

    public AuthenticationContext authenticate(String userID, Object credential) {

        AuthenticationContext context = new AuthenticationContext();
        boolean isAuthenticated = false;

        try {
            UserStore userStore = getUserStoreOfUser(userID);
            isAuthenticated = userStore.authenticate(userID, credential);
            context.setAuthenticated(isAuthenticated);
            if (isAuthenticated) {
                IdentityObject subject = new IdentityObject(userID);
                subject.setUserStoreID(userStore.getUserStoreID());
                context.setSubject(subject);
            }

        } catch (Exception e) {
            context.setAuthenticated(false);
            context.setFailure(new AuthenticationFailure(e));
        }
        return context;
    }

    public String searchUserFromClaim(String claimAttribute, String claimValue) throws UserStoreException {
        if (!(claimValue.indexOf("/") < 0)) {
            String userName = claimValue.substring(claimValue.indexOf("/") + 1);

            IdentityObject user = identityStoreManager.getUserStores().get(claimValue.substring(0,
                    claimValue.indexOf("/"))).searchUser(userName);

            return user.getClaims().get(claimAttribute);
        } else {
            for (UserStore store : identityStoreManager.getUserStores().values()) {
                try {
                    if (store.searchUser(claimAttribute, claimValue) != null) {
                        return store.searchUser(claimAttribute, claimValue).getUserID();
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }

        return null;
    }

    public List<Group> getGroupsOfUser(String userID) throws UserStoreException {
        UserStore userStore = getUserStoreOfUser(userID);
        if (userStore != null) {
            return userStore.getGroupsOfUser(userID);
        }
        throw new UserStoreException("Could not find user in local user stores");
    }

    public Group getGroup(String groupName) throws UserStoreException {
        return identityStoreManager.getUserStores().get(groupName.substring(0, groupName.indexOf("/")))
                .searchGroup(groupName.substring(groupName.indexOf("/") + 1));
    }

    public List<IdentityObject> getUsersOfGroup(String groupID) {
        return null;
    }


    /**
     * Returns IdentityObject of the user with given ID,
     *
     * @param userID
     * @return
     * @throws UserStoreException
     */
    public IdentityObject searchUser(String userID) throws UserStoreException {
        UserStore userStore = getUserStoreFromMapping(userID);
        IdentityObject user = null;

        if (userStore != null) {
            user = userStore.searchUser(userID);
            if (user != null) {
                user.setUserID(userStore.getUserStoreID());
                UserIDManager.storeUserStoreID(userID, userStore.getUserStoreID());
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
     * @return use IdentityObject
     * @throws UserStoreException
     */
    private IdentityObject searchUserInUserStores(String userID) throws UserStoreException {

        HashMap<String, UserStore> userStores = identityStoreManager.getUserStores();
        IdentityObject user;
        Iterator it = userStores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UserStore userStore = (UserStore) pair.getValue();
            user = userStore.searchUser(userID);
            user.setUserStoreID(userStore.getUserStoreID());
            UserIDManager.storeUserStoreID(userID, userStore.getUserStoreID());
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
        String storeID = UserIDManager.getUserStoreID(userID);
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
            IdentityObject user = searchUserInUserStores(userID);
            userStore = identityStoreManager.getUserStore(user.getUserStoreID());
        }

        if (userStore != null) {
            return userStore;
        } else {
            throw new UserStoreException("User couldnt find in the local user stores");
        }
    }


}
