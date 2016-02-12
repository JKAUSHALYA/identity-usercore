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

import org.wso2.carbon.identity.user.core.model.Permission;
import org.wso2.carbon.identity.user.core.model.Role;

import java.util.List;

/**
 * Authorization store.
 */
public interface AuthorizationStore {

    /**
     * Get roles for the user id.
     * @param userId User id of the user.
     * @return Roles associated to the user.
     */
    public List<Role> getRolesForUser(String userId);

    /**
     * Get roles associated to the group.
     * @param roleName Role name of the role.
     * @return Roles associated to the group.
     */
    public List<Role> getRolesForGroup(String roleName);

    /**
     * Get permissions associated to the role.
     * @param roleName Role name of the required role.
     * @return List of permissions associated to the user.
     */
    public List<Permission> getPermissionsForRole(String roleName);
}
