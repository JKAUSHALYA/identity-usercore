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

package org.wso2.carbon.identity.user.core.stores.inmemory;

import org.wso2.carbon.identity.user.core.model.Permission;
import org.wso2.carbon.identity.user.core.model.Role;
import org.wso2.carbon.identity.user.core.stores.AuthorizationStore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * In memory authorization store.
 */
public class InMemoryAuthorizationStore implements AuthorizationStore {

    private Map<String, List<Role>> userRoles = new HashMap<>();
    private Map<String, List<Permission>> rolePermissions = new HashMap<>();

    public InMemoryAuthorizationStore() {

        Role [] roleArrya = { new Role("admin"), new Role("internal/everyone") };
        Permission [] permissionArray = { new Permission("/permissions/all"), new Permission("/permissions/login")};
        List<Role> roles = Arrays.asList(roleArrya);
        List<Permission> permissions = Arrays.asList(permissionArray);

        roleArrya[0].setPermissions(permissions);
        roleArrya[1].setPermissions(permissions);

        userRoles.put("admin", roles);
        rolePermissions.put(roleArrya[0].getRoleName(), permissions);
        rolePermissions.put(roleArrya[1].getRoleName(), permissions);
    }

    public List<Role> getRoles(String userId) {

        return userRoles.get(userId);
    }

    public List<Permission> getPermissions(String roleName) {

        return rolePermissions.get(roleName);
    }
}
