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

import org.wso2.carbon.identity.user.core.manager.PersistenceManager;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;

/**
 * AbstractUserStore
 */
public abstract class AbstractUserStore extends PersistenceManager {


    public boolean addUser(IdentityObject user) {
        persistUser(user);
        return true;
    }

    protected abstract void persistUser(IdentityObject user);

    public IdentityObject searchUser(String userID) {
        return retrieveUser("userName", userID);
    }

    public IdentityObject searchUser(String claimAttribute, String value) {

        return retrieveUser(claimAttribute, value);
    }

    protected abstract IdentityObject retrieveUser(String claimAttribute, String value);

    public UserRole searchRole(String roleName) {
        return retrieveRole(roleName);
    }

    protected abstract UserRole retrieveRole(String roleName);

    public boolean addRole(UserRole role) {
        persistRole(role);
        return true;
    }

    protected abstract void persistRole(UserRole role);

}
