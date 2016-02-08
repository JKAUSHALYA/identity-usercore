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

package org.wso2.carbon.identity.user.core.impl;

import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.HashMap;
import java.util.Map;

/**
 * InMemoryUserStore
 */
public class InMemoryUserStore extends AbstractUserStore {
    protected Map<String, IdentityObject> users;
    protected Map<String, UserRole> roles;

    public InMemoryUserStore() {
        users = new HashMap<>();
        roles = new HashMap<String, UserRole>();
    }

    @Override
    protected void persistUser(IdentityObject user) {
        users.put(user.getUserName(), user);
    }

    @Override
    protected IdentityObject retrieveUser(String claimAttribute, String value) {

        if ("userName".equalsIgnoreCase(claimAttribute)) {
            return users.get(value);
        }
        //TODO: implement logic to return by other claim values by iterating the valueset of the hashmap
        return null;
    }

    @Override
    protected UserRole retrieveRole(String roleName) {
        return roles.get(roleName);
    }

    @Override
    protected void persistRole(UserRole role) {
        roles.put(role.getRoleName(), role);
    }
}
