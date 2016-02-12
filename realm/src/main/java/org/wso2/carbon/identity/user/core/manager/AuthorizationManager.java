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

import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.model.Permission;
import org.wso2.carbon.identity.user.core.model.Role;
import org.wso2.carbon.identity.user.core.stores.AuthorizationStore;

import java.util.List;
import java.util.Map;

/**
 * AuthorizationManager
 */
public class AuthorizationManager implements PersistenceManager {

    /**
     * Checks whether the given user do have the permission.
     * @param userId User id of the user.
     * @param permission Permission that needs to check.
     * @return True if the user has required permission.
     * @throws UserStoreException
     */
    public boolean isUserAuthorized(String userId, Permission permission) throws UserStoreException {

        AuthorizationStoreManager authorizationStoreManager = AuthorizationStoreManager.getInstance();
        Map<String, AuthorizationStore> authorizationStoreList = authorizationStoreManager.getAuthorizationStores();

        for (AuthorizationStore authorizationStore : authorizationStoreList.values()) {
            List<Role> roles = authorizationStore.getRolesForUser(userId);

            for (Role role : roles) {
                List<Permission> permissions = role.getPermissions();

                for (Permission rolePermission : permissions) {

                    if (rolePermission.equals(permission)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
