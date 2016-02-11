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

package org.wso2.carbon.identity.user.core;

import org.wso2.carbon.identity.user.core.config.UserStoreConfig;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.model.Group;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;

import java.util.List;

/**
 * User store.
 */
public interface UserStore {

    /**
     * Initialize user store by passing user store configurations read from files.
     * @param userStoreConfig UserStoreConfigurations.
     * @throws UserStoreException
     */
    void init(UserStoreConfig userStoreConfig) throws UserStoreException;

    /**
     * Get user store's execution order ID.
     * @return Execution order ID.`
     */
    int getExecutionOrder();

    /**
     * Get the name of the user store.
     * @return Name of the user store.
     */
    String getUserStoreName();

    /**
     * Get user store ID which is unique for a user store.
     * @return returns the unique id for the user store
     */
    String getUserStoreID();

    /**
     * Authenticate user with unique userID and any type of credentials
     * @param userID Unique user ID
     * @param credential Credentials Object
     * @return Authentication result, true if successfully authenticated, unless false
     * @throws UserStoreException
     */
    boolean authenticate(String userID, Object credential) throws UserStoreException;

    /**
     * Checks whether a with given user id exists in the user store
     * @param userID Unique user ID
     * @return True if the user is in user store, False if user is not avalable.
     * @throws UserStoreException
     */
    boolean isExistingUser(String userID) throws UserStoreException;

    /**
     * List all users in User Store.
     * @param claimAttribute Claim attribute to be searched
     * @param filter search filter to be applied while searching users
     * @param maxItemLimit
     * @return List of Identities which matches the given claim attribute with given filter
     * @throws UserStoreException
     */
    List<IdentityObject> listUsers(String claimAttribute, String filter, int maxItemLimit) throws UserStoreException;

    /**
     * Search user from user id
     * @param userID User Id of the user
     * @return Identity Object with
     * @throws UserStoreException
     */
    IdentityObject searchUser(String userID) throws UserStoreException;

    IdentityObject searchUser(String claimAttribute, String value) throws UserStoreException;

    Group searchGroup(String groupID) throws UserStoreException;

    Group searchGroup(String attribute, String value) throws UserStoreException;

    List<Group> listGroups(String attribute, String filter, int maxItemLimit) throws UserStoreException;

    List<Group> getGroupsOfUser(String userID) throws UserStoreException;

    List<IdentityObject> getUsersOfGroup(String groupID) throws UserStoreException;

    boolean isExistingRole(String roleID) throws UserStoreException;

    boolean isReadOnly() throws UserStoreException;

    UserStoreConfig getUserStoreConfig();

    void setUserStoreConfig(UserStoreConfig userStoreConfig);

}
