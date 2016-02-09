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
import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.UserStoreException;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.model.UserRole;
import org.wso2.carbon.identity.user.core.util.AuthenticationFailure;

import java.util.ArrayList;

/**
 * IdentityManager
 */
public class IdentityManager implements PersistenceManager {

    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

    private IdentityStoreManager identityStoreManager = IdentityStoreManager.getInstance();

    public IdentityStoreManager getIdentityStoreManager() {
        return this.identityStoreManager;
    }


    public AuthenticationContext authenticate(Object credential) throws UserStoreException {
        return null;
    }

    public AuthenticationContext authenticate(String userid, char[] password) throws UserStoreException {
        return null;
    }

    public AuthenticationContext authenticate(String userid, Object credential) {

        AuthenticationContext context = new AuthenticationContext();
        boolean isAuthenticated = false;
        IdentityObject subject = null;
        try {

            if (!(userid.indexOf("/") < 0)) {
                String userName = userid.substring(userid.indexOf("/") + 1);
                UserStore userStore = IdentityStoreManager.getInstance().getUserStore(userid.substring(0, userid
                        .indexOf("/")));
                isAuthenticated = userStore.authenticate(userName, credential);
                if (!isAuthenticated) {
                    throw new UserStoreException("Error while authenticating against user store");
                }
                IdentityObject user = userStore.searchUser(userName);
                if (isAuthenticated) {
                    context.setSubject(user);
                }
                //return user.getPassword().equals(credential);
            } else {
                for (UserStore store : IdentityStoreManager.getInstance().getUserStores().values()) {
                    if (store.searchUser(userid).getPassword().equals(credential)) {
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

                IdentityObject user = IdentityStoreManager.getInstance().getUserStores().get(claimValue.substring(0,
                        claimValue.indexOf("/"))).searchUser(userName);

                return user.getUserName();
            } else {
                for (UserStore store : IdentityStoreManager.getInstance().getUserStores().values()) {
                    try {
                        if (store.searchUser(claimValue) != null) {
                            return store.searchUser(claimValue).getUserName();
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
        //TODO: create authentication context with user, user store and other information
        if (claimAttribute.equals("userName")) {
            if (!(claimValue.indexOf("/") < 0)) {

            } else {
                for (UserStore store : IdentityStoreManager.getInstance().getUserStores().values()) {
                    try {
                        if (store.searchUser(claimValue).getPassword().equals(credential)) {
                            log.debug("User present");
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return new AuthenticationContext();
    }

    private ArrayList<String> prependStoreName(String storeName, ArrayList<String> nameList) throws UserStoreException {
        ArrayList<String> temp = new ArrayList<String>();

        for (String name : nameList) {
            temp.add(storeName + "/" + name);
        }

        return temp;
    }

    public ArrayList<String> getRolesOfUser(String username) throws UserStoreException {
        String storeName = "PRIMARY";
        String userName = username;
        if (!(username.indexOf("/") < 0)) {

            storeName = username.substring(0, username.indexOf("/"));
            userName = username.substring(username.indexOf("/") + 1);
        }
        return prependStoreName(storeName, IdentityStoreManager.getInstance().getUserStores().get(storeName)
                .searchUser("userName", userName).getMemberOf());
    }

    public UserRole getRole(String roleName) throws UserStoreException {
        return IdentityStoreManager.getInstance().getUserStores().get(roleName.substring(0, roleName.indexOf("/")))
                .searchRole(roleName.substring(roleName.indexOf("/") + 1));
    }

}
