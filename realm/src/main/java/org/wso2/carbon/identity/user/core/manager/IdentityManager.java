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

    public AuthenticationContext authenticate(String userid, Object credential) {

        AuthenticationContext context = new AuthenticationContext();
        boolean isAuthenticated = false;

        try {

            if (userid.contains("/")) {
                String userName = userid.substring(userid.indexOf("/") + 1);
                UserStore userStore = identityStoreManager.getUserStore(userid.substring(0, userid
                        .indexOf("/")));
                isAuthenticated = userStore.authenticate(userName, credential);
                if (!isAuthenticated) {
                    throw new UserStoreException("Error while authenticating against user store");
                }
                IdentityObject user = userStore.searchUser(userName);
                context.setSubject(user);
                //return user.getPassword().equals(credential);
            } else {
                for (UserStore store : identityStoreManager.getUserStores().values()) {
                    if (store.authenticate(userid, credential)) {
                        context.setSubject(store.searchUser(userid));
                        isAuthenticated = true;
                    }
                }
            }

            context.setAuthenticated(isAuthenticated);

        } catch (Exception e) {
            context.setAuthenticated(false);
            context.setFailure(new AuthenticationFailure(e));
        }
        return context;
    }

    public String searchUserFromClaim(String claimAttribute, String claimValue) throws UserStoreException {
        if (claimAttribute.equals("userName")) {
            if (!(claimValue.indexOf("/") < 0)) {
                String userName = claimValue.substring(claimValue.indexOf("/") + 1);

                IdentityObject user = identityStoreManager.getUserStores().get(claimValue.substring(0,
                        claimValue.indexOf("/"))).searchUser(userName);

                return user.getClaims().get("userName");
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
        }
        return null;
    }

    public AuthenticationContext authenticate(String claimAttribute, String claimValue, Object credential) throws
            UserStoreException {
        AuthenticationContext context = new AuthenticationContext();
        if (claimAttribute.equals("userName")) {
            if (!(claimValue.indexOf("/") < 0)) {

            } else {
                for (UserStore store : identityStoreManager.getUserStores().values()) {
                    try {
                        IdentityObject user = store.searchUser(claimValue);
                        if (user != null) {
                            boolean isAuthenticated = store.authenticate(user.getUserID(), credential);
                            if (isAuthenticated) {
                                context.setAuthenticated(true);
                                context.setSubject(user);
                                log.debug("User authenticated successfully");
                                return context;
                            }
                        }
                    } catch (UserStoreException e) {
                        log.debug("Authentication failed for user store " + store.getUserStoreID());
                    }
                }
            }
        }
        context.setAuthenticated(false);
        context.setFailure(new AuthenticationFailure(new Exception("Could not find user in local user stores")));
        return context;
    }

    public List<Group> getGroupsOfUser(String userID) throws UserStoreException {
        UserStore userStore = getUserStoreFromMapping(userID);
        if (userStore == null) {
            userStore = identityStoreManager.getUserStore(searchUserInUserStores(userID).getUserStoreID());

        }
        if (userStore != null) {
            return userStore.getGroupsOfUser(userID);
        }

        throw new UserStoreException("Could not find user in local user stores");
    }

    public Group getGroup(String groupName) throws UserStoreException {
        return identityStoreManager.getUserStores().get(groupName.substring(0, groupName.indexOf("/")))
                .searchGroup(groupName.substring(groupName.indexOf("/") + 1));
    }

    private IdentityObject modifyUserID(IdentityObject user) {
        user.setUserID(user.getUserStoreID() + "--" + user.getUserID());
        return user;
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
                modifyUserID(user);
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
            modifyUserID(user);
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
                UserIDManager.storeUserStoreID(userID, userStore.getUserStoreID());
                return userStore;
            } else {
                throw new UserStoreException("No user store found for the given user store ID");
            }
        } else {
            return null;
        }

    }

}
