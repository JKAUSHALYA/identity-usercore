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

import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.VirtualIdentityStore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Group represents a group of users.
 */
public class Group {

    private String groupID;
    private String userStoreID;
    private String groupName;
    private VirtualIdentityStore virtualIdentityStore = new VirtualIdentityStore();

    public Group(String groupID, String userStoreID, String groupName) throws UserStoreException {
        this.groupID = groupID;
        this.userStoreID = userStoreID;
        this.groupName = groupName;
    }

    /**
     * Get the name of the Group.
     * @return Name of the Group.
     */
    public String getName() {
        return groupName;
    }

    /**
     * Get the users assigned to this group.
     * @return List of users assigned to this group.
     */
    public List<User> getUsers() throws UserStoreException {
        return virtualIdentityStore.getUsersOfGroup(groupID, userStoreID);
    }

    /**
     * Get Permissions assigned to this Group.
     * @return List of Permissions.
     */
    public List<Permission> getPermissions() {
        return null;
    }

    /**
     * Get Roles assigned to this Group.
     * @return List of Roles.
     */
    public List<Group> getRoles() {
        throw new NotImplementedException();
    }

    /**
     * Checks whether this Group is authorized for given Permission.
     * @param permission Permission to be checked.
     * @return True if authorized.
     */
    public boolean isAuthorized(Permission permission) {
        throw new NotImplementedException();
    }

    /**
     * Checks whether the User in this Group.
     * @param user User to be checked.
     * @return True if User is in this Group.
     */
    public boolean hasUser(User user) {
        throw new NotImplementedException();
    }

    /**
     * Checks whether this Group has the Role.
     * @param role Role to be checked.
     * @return True if this Group has the Role.
     */
    public boolean hasRole(Role role) {
        throw new NotImplementedException();
    }

    /**
     * Add a new User list by <b>replacing</b> the existing User list. (PUT)
     * @param newUserList List of Users needs to be assigned to this Group.
     */
    public void updateUsers(List<User> newUserList) {
    }

    /**
     * Assign a new list of Users to existing list and/or un-assign Users from existing list. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updateUsers(List<User> assignList, List<User> unAssignList) {
    }

    /**
     * Add a new Role list by <b>replacing</b> the existing Role list. (PUT)
     * @param newRoleList List of Roles needs to be assigned to this Group.
     */
    public void updateRoles(List<Role> newRoleList) {
    }

    /**
     * Assign a new list of Roles to existing list and/or un-assign Roles from existing list. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updateRoles(List<Role> assignList, List<Role> unAssignList) {
    }

}
