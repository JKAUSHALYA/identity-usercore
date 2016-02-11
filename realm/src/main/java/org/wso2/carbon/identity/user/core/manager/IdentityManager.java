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
import org.wso2.carbon.identity.user.core.model.Role;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.UserStore;

import java.util.ArrayList;


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
                        if (user != null && store.authenticate(user.getUserID(), credential)) {
                            context.setAuthenticated(true);
                            context.setSubject(user);
                            log.debug("User present");
                            return context;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        context.setFailure(new AuthenticationFailure(new Exception("Could not find user in local userstores")));
        return context;
    }

    private ArrayList<String> prependStoreName(String storeName, ArrayList<String> nameList) throws UserStoreException {

        ArrayList<String> temp = new ArrayList<>();

        for (String name : nameList) {
            temp.add(storeName + "/" + name);
        }

        return temp;
    }

    public ArrayList<String> getRolesOfUser(String username) throws UserStoreException {

        String storeName = "PRIMARY";

        if (username.contains("/")) {
            storeName = username.substring(0, username.indexOf("/"));
            username = username.substring(username.indexOf("/") + 1);
        }
        return prependStoreName(storeName, identityStoreManager.getUserStores().get(storeName)
                .searchUser("userName", username).getMemberOf());
    }

    public Role getRole(String roleName) throws UserStoreException {
        return identityStoreManager.getUserStores().get(roleName.substring(0, roleName.indexOf("/")))
                .searchRole(roleName.substring(roleName.indexOf("/") + 1));
    }

}
