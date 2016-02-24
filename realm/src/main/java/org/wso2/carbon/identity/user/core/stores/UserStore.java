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

package org.wso2.carbon.identity.user.core.stores;

import org.wso2.carbon.identity.user.core.config.UserStoreConfig;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.model.Group;
import org.wso2.carbon.identity.user.core.principal.User;

import java.util.List;
import java.util.Map;

/**
 * User store.
 */
public interface UserStore {

    /**
     * Initialize user store by passing user store configurations read from files.
     *
     * @param userStoreConfig UserStoreConfigurations.
     * @throws UserStoreException
     */
    void init(UserStoreConfig userStoreConfig) throws UserStoreException;

    /**
     * Get user store's execution order ID.
     *
     * @return Execution order ID.`
     */
    int getExecutionOrder();

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
     * Authenticate user with unique userID and any type of credentials
     *
     * @param userID     Unique user ID
     * @param credential Credentials Object
     * @return Authentication result, true if successfully authenticated, unless false
     * @throws UserStoreException
     */
    boolean authenticate(String userID, Object credential) throws UserStoreException;

    /**
     * Search user from user id
     *
     * @param userID User Id of the user
     * @return Identity Object with
     * @throws UserStoreException
     */
    User getUser(String userID) throws UserStoreException;

    /**
     * List all users in User Store.
     *
     * @param claimAttribute Claim attribute to be searched
     * @param filter         search filter to be applied while searching users
     * @return List of Identities which matches the given claim attribute with given filter
     * @throws UserStoreException
     */
    List<User> listUsers(String claimAttribute, String filter) throws UserStoreException;

    /**
     * Retrieve set of claims of the user with the given ID
     *
     * @param userID ID of the user whose claims are requested
     * @return Claims map of the user with given ID
     * @throws UserStoreException
     */
    Map<String, String> getUserClaimValues(String userID) throws UserStoreException;

    /**
     * Retrieve group with given group ID
     *
     * @param groupID Unique ID of the group
     * @return Group with the given GroupID
     * @throws UserStoreException
     */
    Group getGroup(String groupID) throws UserStoreException;

    /**
     * List Groups with given filter for given attribute.
     *
     * @param attribute    attribute to be filtered
     * @param filter       filter to be applied on the attribute value
     * @param maxItemLimit maximum limit of groups to be returned
     * @return List of Groups which matches the given filter for given attribute
     * @throws UserStoreException
     */
    List<Group> listGroups(String attribute, String filter, int maxItemLimit) throws UserStoreException;

    /**
     * @param userID Retrieve groups of a given User with unique ID
     * @return List of Groups which this user is assigned to
     * @throws UserStoreException
     */
    List<Group> getGroupsOfUser(String userID) throws UserStoreException;

    /**
     * Retrieve set of users belongs to a group
     *
     * @param groupID Unique ID of the group
     * @return Set of IdentityObjects resides in Group
     * @throws UserStoreException
     */
    List<User> getUsersOfGroup(String groupID) throws UserStoreException;

    User addUser(Map<String, String> claims, Object credential, List<String> groupList, boolean
            requirePasswordChange) throws UserStoreException;

    void updateCredential(String userID, Object newCredential, Object oldCredential)
            throws UserStoreException;

    void updateCredential(String userID, Object newCredential) throws UserStoreException;

    void setUserClaimValues(String userID, Map<String, String> claims) throws UserStoreException;

    void deleteUserClaimValues(String userID, List<String> claims) throws UserStoreException;

    void deleteUser(String userID) throws UserStoreException;

    void deleteGroup(String roleName) throws UserStoreException;

    /**
     * To check whether a user store is read only
     *
     * @return True if the user store is read only, unless returns false
     * @throws UserStoreException
     */
    boolean isReadOnly() throws UserStoreException;

    /**
     * Returns UserStoreConfig which consists of user store configurations.
     *
     * @return UserStoreConfig which consists of user store configurations
     */
    UserStoreConfig getUserStoreConfig();
}
