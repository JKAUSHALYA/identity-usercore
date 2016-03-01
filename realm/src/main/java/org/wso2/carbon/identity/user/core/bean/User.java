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

package org.wso2.carbon.identity.user.core.bean;

import org.wso2.carbon.identity.user.core.exception.AuthorizationFailure;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.VirtualAuthorizationStore;
import org.wso2.carbon.identity.user.core.manager.VirtualIdentityStore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Map;

/**
 * Represents a user in the user core. All of the user related identity operations can be
 * done through this class.
 */
public class User {

    private String userID;
    private String userStoreID;
    private String tenantId;
    private String subject;

    private VirtualIdentityStore virtualIdentityStore;
    private VirtualAuthorizationStore virtualAuthorizationStore = new VirtualAuthorizationStore();

    public User(String userID, String userStoreID) throws UserStoreException {

        this.userID = userID;
        this.userStoreID = userStoreID;
        virtualIdentityStore = new VirtualIdentityStore();
    }

    /**
     * Get the fully qualified name of this user.
     * @return Fully qualified name as a String.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Get claims of this user.
     * @return Map of User claims.
     * @throws UserStoreException
     */
    public Map<String, String> getClaims() throws UserStoreException {
        return virtualIdentityStore.getUserClaimValues(userID);
    }

    /**
     * Get claims of this user for given URIs.
     * @param claimURIs Claim URIs that needs to be retrieved.
     * @return Map of User claims.
     * @throws UserStoreException
     */
    public Map<String, String> getClaims(List<String> claimURIs) throws UserStoreException {
        return virtualIdentityStore.getUserClaimValues(userID, claimURIs);
    }

    /**
     * Get the groups assigned to this user.
     * @return List of Groups assigned to this user.
     * @throws UserStoreException
     */
    public List<Group> getGroups() throws UserStoreException {
        return virtualIdentityStore.getGroupsOfUser(userID);
    }

    /**
     * Get the roles assigned to this user.
     * @return List of Roles assigned to this user.
     */
    public List<Role> getRoles() {
        return null;
    }

    /**
     * Checks whether this user is authorized for given Permission.
     * @param permission Permission that should check on this user.
     * @return True if authorized.
     * @throws AuthorizationFailure
     */
    public boolean isAuthorized(Permission permission) throws AuthorizationFailure {
        return virtualAuthorizationStore.isUserAuthorized(userID, permission);
    }

    /**
     * Checks whether this User is in the given Role.
     * @param roleName Name of the Role.
     * @return True if this user is in the Role.
     */
    public boolean isInRole(String roleName) {
        return virtualAuthorizationStore.isUserInRole(userID, roleName);
    }

    /**
     * Checks whether this user is in the given Group.
     * @param groupName Name of the Group.
     * @return True if this User is in the group.
     */
    public boolean isInGroup(String groupName) {
        return virtualIdentityStore.isUserInGroup(userID, groupName);
    }

    /**
     * Rename this user.
     * @param newUsername New user name.
     */
    public void rename(String newUsername) {
        throw new NotImplementedException();
    }

    /**
     * Set claims for this User.
     * @param claims List of claims to be set.
     */
    public void setClaims(Map<String, String> claims) {
        throw new NotImplementedException();
    }

    /**
     * Add a new Role list by <b>replacing</b> the existing group list. (PUT)
     * @param newGroupList New group list that needs to replace the existing list.
     */
    public void updateGroups(List<Group> newGroupList) {
        virtualIdentityStore.updateGroupsInUser(userID, newGroupList);
    }

    /**
     * Assign a new list of Groups to existing list and/or un-assign Groups from existing Groups. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updateGroups(List<Group> assignList, List<Group> unAssignList) {
        virtualIdentityStore.updateGroupsInUser(userID, assignList, unAssignList);
    }

    /**
     * Add a new Role list by <b>replacing</b> the existing Role list. (PUT)
     * @param newRolesList List of Roles needs to be assigned to this User.
     */
    public void updateRoles(List<Role> newRolesList) {
        virtualAuthorizationStore.updateRolesInUser(userID, newRolesList);
    }

    /**
     * Assign a new list of Roles to existing list and/or un-assign Roles from existing list. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updateRoles(List<Role> assignList, List<Role> unAssignList) {
        virtualAuthorizationStore.updateRolesInUser(userID, assignList, unAssignList);
    }
}
