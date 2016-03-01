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

import org.apache.log4j.Logger;
import org.wso2.carbon.identity.user.core.exception.AuthorizationFailure;
import org.wso2.carbon.identity.user.core.exception.AuthorizationStoreException;
import org.wso2.carbon.identity.user.core.bean.Permission;
import org.wso2.carbon.identity.user.core.bean.Role;
import org.wso2.carbon.identity.user.core.stores.AuthorizationStore;
import org.wso2.carbon.identity.user.core.stores.inmemory.InMemoryAuthorizationStore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * VirtualAuthorizationStore
 */
public class VirtualAuthorizationStore {

    private static Logger log = Logger.getLogger(VirtualAuthorizationStore.class);
    private AuthorizationStore authorizationStore;

    public VirtualAuthorizationStore() {
        authorizationStore = new InMemoryAuthorizationStore();
    }

    /**
     * Checks whether the given user do have the permission.
     * @param userId User id of the user.
     * @param permission Permission that needs to check on.
     * @return True if the user has required permission.
     */
    public boolean isUserAuthorized(String userId, Permission permission) throws AuthorizationFailure {

        List<Role> roles = authorizationStore.getRolesForUser(userId);
        if (roles == null) {
            throw new AuthorizationFailure("No roles assigned for this user");
        }

        for (Role role : roles) {
            List<Permission> permissions = authorizationStore.getPermissionsForRole(role.getName());
            if (permissions == null) {
                continue;
            }
            for (Permission rolePermission : permissions) {
                if (rolePermission.getPermissionString().equals(permission.getPermissionString())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks whether role is authorized.
     * @param roleId Id of the Role.
     * @param permission
     * @return
     */
    public boolean isRoleAuthorized(String roleId, Permission permission) {
        return false;
    }

    public boolean isGroupAuthorized(String groupId, Permission permission) {
        throw new NotImplementedException();
    }

    public boolean isUserInRole(String userId, String roleName) {
        throw new NotImplementedException();
    }


    public void updateRolesInUser(String userId, List<Role> rolesToBeAssign) {
    }

    public void updateRolesInUser(String userId, List<Role> rolesToBeAssign, List<Role> rolesToUnBeAssign) {
    }

    public void assignPermissionsToRole(String roleName, String permissionName) throws AuthorizationStoreException {
        authorizationStore.addRolePermission(roleName, permissionName);
    }
}
