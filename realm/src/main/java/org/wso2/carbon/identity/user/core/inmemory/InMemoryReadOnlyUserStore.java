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

package org.wso2.carbon.identity.user.core.inmemory;

import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.UserStoreConstants;
import org.wso2.carbon.identity.user.core.UserStoreException;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * InMemoryUserStore
 */
public class InMemoryReadOnlyUserStore extends AbstractUserStore {

    protected Map<String, IdentityObject> users;
    protected Map<String, UserRole> roles;

    public InMemoryReadOnlyUserStore() throws UserStoreException{

        super.init(readUserStoreConfig("PRIMARY.properties"));
        users = new HashMap<>();
        roles = new HashMap<String, UserRole>();

        IdentityObject user = new IdentityObject();

        user.setUserName("admin");
        user.setPassword("password");
        user.addRole("ADMIN");

        UserRole role = new UserRole();
        role.setRoleName("ADMIN");
        ArrayList<String> permissions = new ArrayList<String>();
        permissions.add("/permissions/login");
        role.setPermissions(permissions);
        ArrayList<String> members = new ArrayList<String>();
        members.add("admin");
        role.setMembers(members);

        users.put("admin", user);
        roles.put("ADMIN", role);

        HashMap<String, UserStore> stores = new HashMap<String, UserStore>();
    }

    public boolean authenticate(String userid, Object credential) throws UserStoreException {
       return users.get(userid).getPassword().equals(credential);
    }

    @Override
    public boolean isExistingUser(String userName) throws UserStoreException {
        return false;
    }

    @Override
    public boolean isExistingRole(String roleName) throws UserStoreException {
        return false;
    }

    @Override
    public String[] getRoleNames() throws UserStoreException {
        return new String[0];
    }

    @Override
    public String[] getRoleListOfUser(String userName) throws UserStoreException {
        return new String[0];
    }

    @Override
    public String[] getUserListOfRole(String roleName) throws UserStoreException {
        return new String[0];
    }

    @Override
    public String[] listUsers(String filter, int maxItemLimit) throws UserStoreException {
        return new String[0];
    }

    @Override
    public int getExecutionOrder() {
        return Integer.getInteger(getUserStoreProperties().getProperty(UserStoreConstants.EXECUTION_ORDER));
    }

    @Override
    public boolean addUser(IdentityObject user) throws UserStoreException {
        throw new UnsupportedOperationException("Cannot add users in ReadOnly in-memory user store");
    }

    @Override
    public void persistUser(IdentityObject user) throws UserStoreException {
        throw new UnsupportedOperationException("Cannot persist users in ReadOnly in-memory user store");
    }

    @Override
    public IdentityObject searchUser(String userID) throws UserStoreException {
        return users.get(userID);
    }

    @Override
    public IdentityObject searchUser(String claimAttribute, String value) throws UserStoreException {
       return retrieveUser(claimAttribute,value);
    }

    @Override
    public IdentityObject retrieveUser(String claimAttribute, String value) throws UserStoreException {

        if ("userName".equalsIgnoreCase(claimAttribute)) {
            return users.get(value);
        }
        //TODO: implement logic to return by other claim values by iterating the valueset of the hashmap
        return null;
    }

    @Override
    public UserRole retrieveRole(String roleName) throws UserStoreException {
        return roles.get(roleName);
    }

    @Override
    public boolean addRole(UserRole role) throws UserStoreException {
        throw new UnsupportedOperationException("Cannot persist addRole in ReadOnly in-memory user store");
    }

    @Override
    public void persistRole(UserRole role) throws UserStoreException {
        throw new UnsupportedOperationException("Cannot persist roles in ReadOnly in-memory user store");
    }

    @Override
    public boolean isReadOnly() throws UserStoreException {
        return true;
    }

    public UserRole searchRole(String roleName) throws UserStoreException {
        return roles.get(roleName);
    }

    @Override
    public String getUserStoreName() {
        return getUserStoreProperties().getProperty(UserStoreConstants.USER_STORE_NAME);
    }
}
