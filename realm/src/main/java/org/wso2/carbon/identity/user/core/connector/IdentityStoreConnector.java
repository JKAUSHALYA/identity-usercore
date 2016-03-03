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

package org.wso2.carbon.identity.user.core.connector;

import org.wso2.carbon.identity.user.core.bean.Group;
import org.wso2.carbon.identity.user.core.bean.User;
import org.wso2.carbon.identity.user.core.config.UserStoreConfig;
import org.wso2.carbon.identity.user.core.exception.IdentityStoreException;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User store.
 */
public interface IdentityStoreConnector {

    /**
     * Initialize user store by passing user store configurations read from files.
     *
     * @param userStoreConfig UserStoreConfigurations.
     * @throws IdentityStoreException
     */
    void init(UserStoreConfig userStoreConfig) throws IdentityStoreException;

    /**
     * Get the name of the user store.
     *
     * @return Name of the user store.
     */
    String getUserStoreName();

    /**
     * Get user store ID which is unique for a user store.
     *
     * @return returns the unique id for the user store
     */
    String getUserStoreID();

    /**
     * Search user from user id
     *
     * @param userID User Id of the user
     * @return User Object with
     * @throws IdentityStoreException
     */
    User getUser(String userID) throws IdentityStoreException;

    /**
     * Get user from the user name.
     * @param username
     * @return
     * @throws IdentityStoreException
     */
    User getUserByName(String username) throws IdentityStoreException;

    /**
     * List all users in User Store.
     *
     * @param filterPattern Filter pattern to be used.
     * @param offset        Offset to get the Users.
     * @param length        Number of users from the offset.
     * @return List of Identities which matches the given claim attribute with given filter
     * @throws IdentityStoreException
     */
    List<User> listUsers(String filterPattern, int offset, int length) throws IdentityStoreException;

    /**
     * Retrieve set of claims of the user with the given ID
     *
     * @param userID ID of the user whose claims are requested
     * @return Claims map of the user with given ID
     * @throws IdentityStoreException
     */
    Map<String, String> getUserClaimValues(String userID) throws IdentityStoreException;

    /**
     *
     * @param userID
     * @param claimURIs
     * @return
     * @throws IdentityStoreException
     */
    Map<String, String> getUserClaimValues(String userID, Set<String> claimURIs) throws IdentityStoreException;

    /**
     * Retrieve group with given group ID
     *
     * @param groupID Unique ID of the group
     * @return Group with the given GroupID
     * @throws IdentityStoreException
     */
    Group getGroup(String groupID) throws IdentityStoreException;

    /**
     * List Groups with given filter for given attribute.
     *
     * @param attribute    attribute to be filtered
     * @param filter       filter to be applied on the attribute value
     * @param maxItemLimit maximum limit of groups to be returned
     * @return List of Groups which matches the given filter for given attribute
     * @throws IdentityStoreException
     */
    List<Group> listGroups(String attribute, String filter, int maxItemLimit) throws IdentityStoreException;

    /**
     * @param userID Retrieve groups of a given User with unique ID
     * @return List of Groups which this user is assigned to
     * @throws IdentityStoreException
     */
    List<Group> getGroupsOfUser(String userID) throws IdentityStoreException;

    /**
     * Retrieve set of users belongs to a group
     *
     * @param groupID Unique ID of the group
     * @return Set of IdentityObjects resides in Group
     * @throws IdentityStoreException
     */
    List<User> getUsersOfGroup(String groupID) throws IdentityStoreException;

    /**
     * Add an user to the user store.
     * @param claims User claims.
     * @param credential User credentials.
     * @param groupList List of Groups of the user.
     * @return Added user.
     * @throws IdentityStoreException
     */
    User addUser(Map<String, String> claims, Object credential, List<String> groupList) throws IdentityStoreException;

    /**
     * Add a Group.
     * @return Added Group.
     * @throws IdentityStoreException
     */
    Group addGroup(String groupName) throws IdentityStoreException;

    /**
     * Assign Group/s to an User.
     * @param userId Id of the User.
     * @param groups List of Groups to be assign.
     * @throws IdentityStoreException
     */
    void assignGroupsToUser(String userId, List<Group> groups) throws IdentityStoreException;

    /**
     * Assign Users to Group.
     * @param groupId Id of the Group.
     * @param identities List of Users.
     * @throws IdentityStoreException
     */
    void assingUsersToGroup(String groupId, List<User> identities) throws IdentityStoreException;

    /**
     * Add new credential to the User.
     * @param userID ID of the user.
     * @param newCredential New credential.
     * @throws IdentityStoreException
     */
    void updateCredential(String userID, Object newCredential) throws IdentityStoreException;

    /**
     * Update credentials of a user.
     * @param userID User Id.
     * @param oldCredential Old credential.
     * @param newCredential New credential.
     * @throws IdentityStoreException
     */
    void updateCredential(String userID, Object oldCredential, Object newCredential) throws IdentityStoreException;

    /**
     * Set user attributes.
     * @param userID User id.
     * @param attributes Attributes.
     * @throws IdentityStoreException
     */
    void setUserAttributeValues(String userID, Map<String, String> attributes) throws IdentityStoreException;

    /**
     * Delete user attribute/s of user.
     * @param userID Id of the user.
     * @param attributes Attributes.
     * @throws IdentityStoreException
     */
    void deleteUserAttributeValues(String userID, List<String> attributes) throws IdentityStoreException;

    /**
     * Delete an existing user.
     * @param userID Id of the user.
     * @throws IdentityStoreException
     */
    void deleteUser(String userID) throws IdentityStoreException;

    /**
     * Delete a group.
     * @param groupId ID of the Group.
     * @throws IdentityStoreException
     */
    void deleteGroup(String groupId) throws IdentityStoreException;

    /**
     * To check whether a user store is read only     *
     * @return True if the user store is read only, unless returns false
     * @throws IdentityStoreException
     */
    boolean isReadOnly() throws IdentityStoreException;

    /**
     * Returns UserStoreConfig which consists of user store configurations.     *
     * @return UserStoreConfig which consists of user store configurations
     */
    UserStoreConfig getUserStoreConfig();
}
