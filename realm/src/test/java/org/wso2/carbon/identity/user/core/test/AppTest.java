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

package org.wso2.carbon.identity.user.core.test;

import org.junit.Assert;
import org.junit.Test;
import org.wso2.carbon.identity.user.core.common.BasicUserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.exception.AuthenticationFailure;
import org.wso2.carbon.identity.user.core.exception.AuthorizationFailure;
import org.wso2.carbon.identity.user.core.exception.AuthorizationStoreException;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.VirtualAuthorizationStore;
import org.wso2.carbon.identity.user.core.manager.VirtualIdentityStore;
import org.wso2.carbon.identity.user.core.bean.Permission;
import org.wso2.carbon.identity.user.core.bean.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Main test class.
 */
public class AppTest {

    private AuthenticationManager authManager = null;
    private VirtualAuthorizationStore authzManager = null;
    private VirtualIdentityStore virtualIdentityStore = null;

    public void configure() throws UserStoreException {

        authManager = BasicUserRealmService.getInstance().getAuthenticationManager();
        authzManager = BasicUserRealmService.getInstance().getVirtualAuthorizationStore();
        virtualIdentityStore = BasicUserRealmService.getInstance().getVirtualIdentityStore();
    }

    private void addUser() throws UserStoreException, AuthorizationStoreException {

        Map<String, String> userClaims = new HashMap<>();
        userClaims.put("userName", "admin");

        User user = virtualIdentityStore
                .addUser(null, userClaims, "password".toCharArray(), new ArrayList<String>(), false);
        String userId = user.getUserID();

        authzManager.addUserRole(userId, "internal/everyone", "PRIMARY");
        authzManager.addRolePermission("internal/everyone", "/permissions/login", "PRIMARY");
    }

    @Test
    public void testApp() throws UserStoreException, AuthorizationStoreException, AuthorizationFailure,
            AuthenticationFailure {

        configure();
        addUser();

        String userName = "admin";
        String password = "password";

        AuthenticationContext context = authManager.authenticate("userName", userName, password.toCharArray());
        String userId = context.getUser().getUserID();
        Assert.assertTrue(authzManager.isUserAuthorized(userId, new Permission("/permissions/login")));
    }
}
