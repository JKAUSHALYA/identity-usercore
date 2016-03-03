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

package org.wso2.carbon.identity.user.core.store;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.bean.Group;
import org.wso2.carbon.identity.user.core.bean.User;
import org.wso2.carbon.identity.user.core.connector.IdentityStoreConnector;
import org.wso2.carbon.identity.user.core.connector.inmemory.InMemoryUserStoreConnector;
import org.wso2.carbon.identity.user.core.exception.IdentityStoreException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;


/**
 * Represents a virtual identity store to abstract the underlying user connector.
 */
public class IdentityStore {

    private static final Logger log = LoggerFactory.getLogger(IdentityStore.class);
    private static IdentityStoreConnector userStore = new InMemoryUserStoreConnector();;

    /**
     * Get the groups assigned to the specified user.
     * @param userID Id of the user.
     * @return List of Group assigned to the user.
     * @throws IdentityStoreException
     */
    public List<Group> getGroupsOfUser(String userID) throws IdentityStoreException {
        return userStore.getGroupsOfUser(userID);
    }

    /**
     * Get the users assigned to the specified group.
     * @param groupID Id of the group.
     * @param userStoreId User store id of this group.
     * @return List of users assigned to the group.
     * @throws IdentityStoreException
     */
    public List<User> getUsersOfGroup(String groupID, String userStoreId) throws IdentityStoreException {
        return userStore.getUsersOfGroup(groupID);
    }

    /**
     * Returns User of the user with given ID.
     * @param username Id of the user.
     * @return User.
     * @throws IdentityStoreException
     */
    public User getUser(String username) throws IdentityStoreException {
        throw new NotImplementedException();
    }

    /**
     * Get user claim values.
     * @param userID Id of the user.
     * @return Map of user claims.
     * @throws IdentityStoreException
     */
    public Map<String, String> getUserClaimValues(String userID) throws IdentityStoreException {
        return userStore.getUserClaimValues(userID);
    }

    /**
     * Get user's claim values for the given URIs.
     * @param userID Id of the user.
     * @param claimURIs Claim URIs.
     * @return Map of claims.
     * @throws IdentityStoreException
     */
    public Map<String, String> getUserClaimValues(String userID, List<String> claimURIs) throws IdentityStoreException {
        throw new NotImplementedException();
    }

    /**
     * Checks whether the user exists.
     * @param userID Id of the user.
     * @return True if the user is exists.
     * @throws IdentityStoreException
     */
    public boolean isExistingUser(String userID) throws IdentityStoreException {
        throw new NotImplementedException();
    }

    /**
     * Checks whether the user is in the group.
     * @param userId Id of the user.
     * @param groupId Id of the group.
     * @return True if the user is in the group.
     */
    public boolean isUserInGroup(String userId, String groupId) {
        throw new NotImplementedException();
    }

    /**
     * Add an user to the user store.
     * @param claims User claims.
     * @param credential User credentials.
     * @param groupList List of Groups of the user.
     * @return Added user.
     * @throws IdentityStoreException
     */
    public User addUser(Map<String, String> claims, Object credential, List<String> groupList)
            throws IdentityStoreException {
        return userStore.addUser(claims, credential, groupList);
    }

    /**
     * Delete an existing user.
     * @param userID ID of the user.
     * @throws IdentityStoreException
     */
    public void deleteUser(String userID) throws IdentityStoreException {
        userStore.deleteUser(userID);
    }

    /**
     * Rename the user.
     * @param userId Id of the user.
     * @param newName New name.
     */
    public void renameUser(String userId, String newName) {
        throw new NotImplementedException();
    }

    /**
     * Add a new Group list by <b>replacing</b> the existing group list. (PUT)
     * @param userId Id of the user.
     * @param groupsToBeAssign New group list that needs to replace the existing list.
     */
    public void updateGroupsInUser(String userId, List<Group> groupsToBeAssign) {
        throw new NotImplementedException();
    }

    /**
     * Assign a new list of Groups to existing list and/or un-assign Groups from existing Groups. (PATCH)
     * @param userId Id of the user.
     * @param groupsToBeAssign List to be added to the new list.
     * @param groupsToBeUnAssign List to be removed from the existing list.
     */
    public void updateGroupsInUser(String userId, List<Group> groupsToBeAssign, List<Group> groupsToBeUnAssign) {
        throw new NotImplementedException();
    }

    /**
     * Add a new User list by <b>replacing</b> the existing User list. (PUT)
     * @param groupId Id of the group.
     * @param usersToBeAssign List of Users needs to be assigned to this Group.
     */
    public void updateUsersInGroup(String groupId, List<User> usersToBeAssign) {
        throw new NotImplementedException();
    }

    /**
     * Assign a new list of Users to existing list and/or un-assign Users from existing list. (PATCH)
     * @param groupId Id of the group.
     * @param usersToBeAssign List to be added to the new list.
     * @param usersToBeUnAssign List to be removed from the existing list.
     */
    public void updateUsersInGroup(String groupId, List<User> usersToBeAssign, List<User> usersToBeUnAssign) {
        throw new NotImplementedException();
    }
}
