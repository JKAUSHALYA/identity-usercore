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

package org.wso2.carbon.identity.user.core;

import org.wso2.carbon.identity.user.core.config.UserStoreConfig;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.Map;
import java.util.Properties;

public interface UserStore {

    int getExecutionOrder();

    void init(UserStoreConfig userStoreConfig) throws UserStoreException;

    String getUserStoreName();

    boolean authenticate(String userid, Object credential) throws UserStoreException;

    boolean isExistingUser(String userName) throws UserStoreException;

    String[] listUsers(String filter, int maxItemLimit) throws UserStoreException;

    String[] listUsers(String claimAttribute, String filter, int maxItemLimit) throws UserStoreException;

    IdentityObject searchUser(String userID) throws UserStoreException;

    IdentityObject searchUser(String claimAttribute, String value) throws UserStoreException;

    UserRole searchRole(String roleName) throws UserStoreException;

    boolean isExistingRole(String roleName) throws UserStoreException;

    String[] getRoleNames() throws UserStoreException;

    boolean isReadOnly() throws UserStoreException;

    boolean addRole(UserRole role) throws UserStoreException;

    boolean addUser(IdentityObject user) throws UserStoreException;

    void persistUser(IdentityObject user) throws UserStoreException;

    UserStoreConfig getUserStoreConfig();

}
