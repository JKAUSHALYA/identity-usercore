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

package org.wso2.carbon.identity.user.core.stores.inmemory;

import org.wso2.carbon.identity.user.core.UserStoreConstants;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.model.Group;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * InMemoryUserStore
 */
public class InMemoryReadOnlyUserStore extends AbstractUserStore {

    protected Map<String, InMemoryUserStoreUser> users;
    protected Map<String, InMemoryUserStoreGroup> groups;

    public InMemoryReadOnlyUserStore() throws UserStoreException {

        users = new HashMap<>();
        groups = new HashMap<>();

        InMemoryUserStoreUser user = new InMemoryUserStoreUser();
        HashMap<String, String> claims = new HashMap<String, String>();
        List<String> groupList = new ArrayList<String>();
        claims.put("userName", "admin");
        user.setPassword("password".toCharArray());
        groupList.add("ADMIN");
        user.setClaims(claims);
        user.setGroups(groupList);
        user.setUserID("12345");
        users.put("12345", user);

        InMemoryUserStoreGroup group = new InMemoryUserStoreGroup();
        group.setGroupID("12345555555");
        ArrayList<String> members = new ArrayList<String>();
        members.add("12345");
        group.setUsers(members);

        users.put("admin", user);
        groups.put("ADMIN", group);
    }

    public boolean authenticate(String userid, Object credential) throws UserStoreException {
        return String.copyValueOf(users.get(userid).getPassword()).equals(credential);
    }

    @Override
    public boolean isExistingUser(String userName) throws UserStoreException {
        if (users.get(userName) != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistingRole(String groupName) throws UserStoreException {
        if (groups.get(groupName) != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<IdentityObject> listUsers(String claimAttribute, String filter, int maxItemLimit)
            throws UserStoreException {

        ArrayList<IdentityObject> userList = new ArrayList<IdentityObject>();
        Iterator it = users.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue().toString().contains(filter)) {
               InMemoryUserStoreUser user = (InMemoryUserStoreUser) pair.getValue();
                userList.add(new IdentityObject(user.getUserID()));
            }
        }
        return userList;
    }

    @Override
    public int getExecutionOrder() {
        return Integer.getInteger(getUserStoreConfig().getUserStoreProperties().getProperty(UserStoreConstants
                .EXECUTION_ORDER));
    }

    @Override
    public IdentityObject searchUser(String userID) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            return new IdentityObject(user.getUserID());
        }
        throw new UserStoreException("Could not find a user with given userID");
    }

    @Override
    public IdentityObject searchUser(String claimAttribute, String value) throws UserStoreException {
        return retrieveUser(claimAttribute, value);
    }

    @Override
    public Group searchGroup(String groupID) throws UserStoreException {
        InMemoryUserStoreGroup inMemoryUserStoreGroup = this.groups.get(groupID);
        Group group = new Group(inMemoryUserStoreGroup.getGroupID());
        group.setUserStoreID(this.getUserStoreID());
        return group;
    }

    @Override
    public Group searchGroup(String attribute, String value) throws UserStoreException {

        Iterator it = groups.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            InMemoryUserStoreGroup group = (InMemoryUserStoreGroup) pair.getValue();
            return new Group(group.getGroupID());
        }
        return null;
    }

    @Override
    public List<Group> listGroups(String attribute, String filter, int maxItemLimit) throws UserStoreException {
        return null;
    }

    @Override
    public List<Group> getGroupsOfUser(String userID) throws UserStoreException {
        return null;
    }

    @Override
    public List<IdentityObject> getUsersOfGroup(String groupID) throws UserStoreException {
        return null;
    }

    public IdentityObject retrieveUser(String claimAttribute, String value) throws UserStoreException {

        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            InMemoryUserStoreUser inMemoryUserStoreUser = (InMemoryUserStoreUser) pair.getValue();
            String claimValue = inMemoryUserStoreUser.getClaims().get(claimAttribute);
            if (claimValue != null && claimValue.equalsIgnoreCase(value)) {
                return new IdentityObject(inMemoryUserStoreUser.getUserID());
            }
        }
        return null;
    }

    public Group retrieveGroup(String groupName) throws UserStoreException {
        InMemoryUserStoreGroup inMemoryUserStoreGroup = this.groups.get(groupName);
        Group group = new Group(inMemoryUserStoreGroup.getGroupID());
        group.setUserStoreID(this.getUserStoreID());
        return group;
    }


    @Override
    public boolean isReadOnly() throws UserStoreException {
        return Boolean.parseBoolean(getUserStoreConfig().getUserStoreProperties().getProperty(UserStoreConstants
                .READ_ONLY));
    }

    @Override
    public String getUserStoreName() {
        return getUserStoreConfig().getUserStoreProperties().getProperty(UserStoreConstants.USER_STORE_NAME);
    }

    @Override
    public String getUserStoreID() {
        if (this.getUserStoreConfig().getUserStoreProperties().get(UserStoreConstants
                .USER_STORE_ID) != null) {
            return this.getUserStoreConfig().getUserStoreProperties().get(UserStoreConstants
                    .USER_STORE_ID).toString();
        } else {
            return null;
        }
    }
}
