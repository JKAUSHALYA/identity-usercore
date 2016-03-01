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
import org.wso2.carbon.identity.user.core.bean.Group;
import org.wso2.carbon.identity.user.core.bean.User;
import org.wso2.carbon.identity.user.core.stores.UserStore;
import org.wso2.carbon.identity.user.core.stores.inmemory.InMemoryReadOnlyUserStore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.callback.Callback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Represents a virtual identity store to abstract the underlying user stores.
 */
public class VirtualIdentityStore {

    private static final Logger log = LoggerFactory.getLogger(VirtualIdentityStore.class);
    private static UserStore userStore;

    public VirtualIdentityStore() throws UserStoreException {
        userStore = new InMemoryReadOnlyUserStore();
    }

    /**
     * Authenticate the user.
     * @param callbacks Callbacks to get the user details.
     * @return @see{AuthenticationContext} if the authentication is success. @see{AuthenticationFailure} otherwise.
     * @throws AuthenticationFailure
     * @throws UserStoreException
     */
    public AuthenticationContext authenticate(Callback[] callbacks) throws AuthenticationFailure, UserStoreException {

        String userId = userStore.authenticate(callbacks);
        if (userId != null) {
            return new AuthenticationContext(new User(userId, userStore.getUserStoreID()));
        }
        throw new AuthenticationFailure("Invalid user credentials");
    }

    /**
     * Get the groups assigned to the specified user.
     * @param userID Id of the user.
     * @return List of Group assigned to the user.
     * @throws UserStoreException
     */
    public List<Group> getGroupsOfUser(String userID) throws UserStoreException {
        return userStore.getGroupsOfUser(userID);
    }

    /**
     * Get the users assigned to the specified group.
     * @param groupID Id of the group.
     * @param userStoreId User store id of this group.
     * @return List of users assigned to the group.
     * @throws UserStoreException
     */
    public List<User> getUsersOfGroup(String groupID, String userStoreId) throws UserStoreException {
        return userStore.getUsersOfGroup(groupID);
    }

    /**
     * Returns User of the user with given ID.
     * @param username Id of the user.
     * @return User.
     * @throws UserStoreException
     */
    public User searchUser(String username) throws UserStoreException {
        throw new NotImplementedException();
    }

    /**
     * Get user claim values.
     * @param userID Id of the user.
     * @return Map of user claims.
     * @throws UserStoreException
     */
    public Map<String, String> getUserClaimValues(String userID) throws UserStoreException {
        return userStore.getUserClaimValues(userID);
    }

    /**
     *
     * @param userID
     * @param claimURIs
     * @return
     * @throws UserStoreException
     */
    public Map<String, String> getUserClaimValues(String userID, List<String> claimURIs) throws UserStoreException {
        throw new NotImplementedException();
    }

    /**
     * Add an user to the user store.
     * @param claims User claims.
     * @param credential User credentials.
     * @param groupList List of Groups of the user.
     * @return Added user.
     * @throws UserStoreException
     */
    public User addUser(Map<String, String> claims, Object credential, List<String> groupList)
            throws UserStoreException {
        return userStore.addUser(claims, credential, groupList);
    }

    /**
     * Delete an existing user.
     * @param userID ID of the user.
     * @throws UserStoreException
     */
    public void deleteUser(String userID) throws UserStoreException {
        userStore.deleteUser(userID);
    }

    public boolean isExistingUser(String userID) throws UserStoreException {
        throw new NotImplementedException();
    }

    public void updateGroupsInUser(String userId, List<Group> groupsToBeAssign) {
    }

    public void updateGroupsInUser(String userId, List<Group> groupsToBeAssign, List<Group> groupsToBeUnAssign) {
    }

    public boolean isUserInGroup(String userId, String groupName) {
        throw new NotImplementedException();
    }
}
