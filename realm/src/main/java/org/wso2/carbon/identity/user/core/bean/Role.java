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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * Represents a Role.
 */
public class Role {

    private String roleName;
    private String roleId;

    public Role(String roleName, String roleId) {
        this.roleName = roleName;
    }

    /**
     * Get the name of this Role
     * @return Role name.
     */
    public String getName() {
        return roleName;
    }

    /**
     * Get the users assigned to this role.
     * @return List of users assigned to this role.
     */
    public List<User> getUsers() {
        throw new NotImplementedException();
    }

    /**
     * Get all Permissions assign to this Role.
     * @return List of Permission.
     */
    public List<Permission> getPermissions() {
        return null;
    }

    /**
     * Get all Groups assigned to this Role.
     * @return List of Group.
     */
    public List<Group> getGroups() {
        throw new NotImplementedException();
    }

    /**
     * Checks whether this Role is authorized for given Permission.
     * @param permission Permission to be checked.
     * @return True if authorized.
     */
    public boolean isAuthorized(Permission permission) {
        throw new NotImplementedException();
    }

    /**
     * Checks whether the User is in this Role.
     * @param user User to be checked.
     * @return True if User exists.
     */
    public boolean hasUser(User user) {
        throw new NotImplementedException();
    }

    /**
     * Checks whether the Group is in this Role.
     * @param group Group to be checked.
     * @return True if the Group exists.
     */
    public boolean hasGroup(Group group) {
        throw new NotImplementedException();
    }

    /**
     * Add a new Permission list by <b>replacing</b> the existing Permission list. (PUT)
     * @param newPermissionList New Permission list that needs to replace the existing list.
     */
    public void updatePermissions(List<Permission> newPermissionList) {
    }

    /**
     * Assign a new list of Permissions to existing list and/or un-assign Permission from existing Permission. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updatePermissions(List<Permission> assignList, List<Permission> unAssignList) {
    }

    /**
     * Add a new User list by <b>replacing</b> the existing User list. (PUT)
     * @param newUserList New User list that needs to replace the existing list.
     */
    public void updateUsers(List<User> newUserList) {
    }

    /**
     * Assign a new list of User to existing list and/or un-assign Permission from existing User. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updateUsers(List<User> assignList, List<User> unAssignList) {
    }

    /**
     * Add a new Group list by <b>replacing</b> the existing Group list. (PUT)
     * @param newGroupList New Group list that needs to replace the existing list.
     */
    public void updateGroups(List<Group> newGroupList) {
    }

    /**
     * Assign a new list of Group to existing list and/or un-assign Group from existing Group. (PATCH)
     * @param assignList List to be added to the new list.
     * @param unAssignList List to be removed from the existing list.
     */
    public void updateGroups(List<Group> assignList, List<Group> unAssignList) {
    }
}