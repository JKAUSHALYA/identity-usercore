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

import org.wso2.carbon.identity.user.core.exception.AuthenticationFailure;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.bean.Group;
import org.wso2.carbon.identity.user.core.bean.User;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;
import org.wso2.carbon.identity.user.core.stores.UserStoreConstants;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

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
        Map<String, String> claims = new HashMap<>();
        claims.put("username", "admin");
        String uuid = UUID.randomUUID().toString();
        user.setUserID(uuid);
        user.setClaims(claims);
        user.setPassword(new char [] {'a', 'd', 'm', 'i', 'n'});
        users.put(uuid, user);

        InMemoryUserStoreGroup group = new InMemoryUserStoreGroup();
        group.setGroupID("12345555555");
        ArrayList<String> members = new ArrayList<String>();
        members.add("12345");
        group.setUsers(members);
        groups.put("ADMIN", group);
    }

    public String authenticate(Callback[] callbacks) throws UserStoreException, AuthenticationFailure {

        String username = null;
        char [] password = null;
        String claimAttribute = "username";

        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                username = ((NameCallback) callback).getName();
            } else if (callback instanceof PasswordCallback) {
                password = ((PasswordCallback) callback).getPassword();
            }
        }

        if (username == null) {
            throw new AuthenticationFailure("Username is null");
        }

        for (InMemoryUserStoreUser user : users.values()) {
            if (user.getClaims().get(claimAttribute) != null &&
                    user.getClaims().get(claimAttribute).equals(username)) {
                if (Arrays.equals(password, user.getPassword())) {
                    return user.getUserID();
                }
            }
        }
        return null;
    }

    public boolean isExistingUser(String userName) throws UserStoreException {
        if (users.get(userName) != null) {
            return true;
        }
        return false;
    }

    public boolean isExistingRole(String groupName) throws UserStoreException {
        if (groups.get(groupName) != null) {
            return true;
        }
        return false;
    }

    public List<User> listUsers(String claimAttribute, String filter)
            throws UserStoreException {

        ArrayList<User> userList = new ArrayList<>();

        for (InMemoryUserStoreUser user : users.values()) {

            if (user.getClaims().containsKey(claimAttribute) && user.getClaims().containsValue(filter)) {
                // userList.add(new User(user.getUserID()));
            }
        }
        return userList;
    }

    public int getExecutionOrder() {
        return Integer.getInteger(getUserStoreConfig().getUserStoreProperties().getProperty(UserStoreConstants
                .EXECUTION_ORDER));
    }

    @Override
    public User getUser(String userID) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            return new User(user.getUserID(), this.getUserStoreID());
        }
        throw new UserStoreException("Could not find a user with given userID");
    }

    @Override
    public User getUserByName(String username) throws UserStoreException {
        return null;
    }

    @Override
    public List<User> listUsers(String filterPattern, int offset, int length) throws UserStoreException {
        return null;
    }

    public User getUser(String claimAttribute, String value) throws UserStoreException {
        return retrieveUser(claimAttribute, value);
    }

    @Override
    public Group getGroup(String groupID) throws UserStoreException {
        InMemoryUserStoreGroup inMemoryUserStoreGroup = this.groups.get(groupID);
        Group group = new Group(inMemoryUserStoreGroup.getGroupID(), this.getUserStoreID(), "");
        return group;
    }

    public Group getGroup(String attribute, String value) throws UserStoreException {

        Iterator it = groups.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            InMemoryUserStoreGroup group = (InMemoryUserStoreGroup) pair.getValue();
            return new Group(group.getGroupID(), "", "");
        }
        return null;
    }

    @Override
    public List<Group> listGroups(String attribute, String filter, int maxItemLimit) throws UserStoreException {
        return null;
    }

    @Override
    public List<Group> getGroupsOfUser(String userID) throws UserStoreException {
        List<Group> groupList = new ArrayList<Group>();
        Iterator it = users.get(userID).getClaims().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            groupList.add(new Group(pair.getValue().toString(), this.getUserStoreID(), ""));
        }
        return groupList;
    }

    @Override
    public List<User> getUsersOfGroup(String groupID) throws UserStoreException {
        return null;
    }

    @Override
    public User addUser(Map<String, String> claims, Object credential, List<String> groupList) throws UserStoreException {
        return null;
    }

    @Override
    public Group addGroup(String groupName) throws UserStoreException {
        return null;
    }

    @Override
    public void assignGroupsToUser(String userId, List<Group> groups) throws UserStoreException {

    }

    @Override
    public void assingUsersToGroup(String groupId, List<User> users) throws UserStoreException {

    }

    @Override
    public Map<String, String> getUserClaimValues(String userID) throws UserStoreException {
        return users.get(userID).getClaims();
    }

    @Override
    public Map<String, String> getUserClaimValues(String userID, Set<String> claimURIs) throws UserStoreException {
        return null;
    }

    public User retrieveUser(String claimAttribute, String value) throws UserStoreException {

        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            InMemoryUserStoreUser inMemoryUserStoreUser = (InMemoryUserStoreUser) pair.getValue();
            String claimValue = inMemoryUserStoreUser.getClaims().get(claimAttribute);
            if (claimValue != null && claimValue.equalsIgnoreCase(value)) {
                return new User(inMemoryUserStoreUser.getUserID(), this.getUserStoreID());
            }
        }
        return null;
    }

    public Group retrieveGroup(String groupName) throws UserStoreException {
        InMemoryUserStoreGroup inMemoryUserStoreGroup = this.groups.get(groupName);
        Group group = new Group(inMemoryUserStoreGroup.getGroupID(), this.getUserStoreID(), "");
        return group;
    }


    @Override
    public boolean isReadOnly() throws UserStoreException {
        return Boolean.parseBoolean(getUserStoreConfig().getUserStoreProperties().getProperty(UserStoreConstants
                .READ_ONLY));
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

    public User addUser(Map<String, String> claims, Object credential, List<String> groupList, boolean
            requirePasswordChange) throws UserStoreException {
        UUID userID = UUID.randomUUID();
        InMemoryUserStoreUser user = new InMemoryUserStoreUser();
        user.setUserID(userID.toString());
        user.setGroups(groupList);
        user.setPassword((char[]) credential);
        user.setClaims(claims);
        this.users.put(user.getUserID() , user);
        return new User(userID.toString(), this.getUserStoreID());
    }

    @Override
    public void updateCredential(String userID, Object newCredential, Object oldCredential) throws UserStoreException {

        boolean isAuthenticated = true; //this.authenticate(userID, oldCredential);
        if (isAuthenticated) {
            InMemoryUserStoreUser user = users.get(userID);
            if (user != null) {
                user.setPassword((char[]) newCredential);
            } else {
                throw new UserStoreException("Couldn't find user inside user store");
            }
        } else {
            throw new UserStoreException("Could not authenticate using old credentials");
        }
    }

    @Override
    public void setUserAttributeValues(String userID, Map<String, String> attributes) throws UserStoreException {

    }

    @Override
    public void deleteUserAttributeValues(String userID, List<String> attributes) throws UserStoreException {

    }

    @Override
    public void updateCredential(String userID, Object newCredential) throws UserStoreException {

        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            user.setPassword((char[]) newCredential);
        } else {
            throw new UserStoreException("Couldn't find user inside user store");
        }
    }

    @Override
    public void deleteUser(String userID) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            users.remove(userID);
        } else {
            throw new UserStoreException("Couldn't find user inside user store");
        }
    }

    @Override
    public void deleteGroup(String groupID) throws UserStoreException {
        InMemoryUserStoreGroup group = groups.get(groupID);
        if (group != null) {
            groups.remove(groupID);
        } else {
            throw new UserStoreException("No group found with the given group ID");
        }
    }

    public void setUserClaimValue(String userID, String claimURI, String claimValue) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            user.getClaims().put(claimURI, claimValue);
        } else {
            throw new UserStoreException("Couldn't find user inside user store");
        }
    }

    public void setUserClaimValues(String userID, Map<String, String> claims) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            user.setClaims(claims);
        } else {
            throw new UserStoreException("Couldn't find user inside user store");
        }
    }

    public void deleteUserClaimValue(String userID, String claimURI) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            user.getClaims().remove(claimURI);
        } else {
            throw new UserStoreException("Couldn't find user inside user store");
        }
    }

    public void deleteUserClaimValues(String userID, List<String> claims) throws UserStoreException {
        InMemoryUserStoreUser user = users.get(userID);
        if (user != null) {
            for (String claim : claims) {
                user.getClaims().remove(claim);
            }
        } else {
            throw new UserStoreException("Couldn't find user inside user store");
        }
    }

    @Override
    public String getUserStoreName() {
        return getUserStoreConfig().getUserStoreProperties().getProperty(UserStoreConstants.USER_STORE_NAME);
    }
}
