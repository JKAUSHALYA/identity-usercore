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
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationStoreManager;
import org.wso2.carbon.identity.user.core.model.Permission;
import org.wso2.carbon.identity.user.core.stores.AuthorizationStore;
import org.wso2.carbon.identity.user.core.stores.inmemory.InMemoryAuthorizationStore;

/**
 * Main test class.
 */
public class AppTest {

    private AuthenticationManager authManager = null;
    private AuthorizationManager authzManager = null;

    public void configure() throws UserStoreException {

        authManager = BasicUserRealmService.getInstance().getAuthenticationManager();
        authzManager = BasicUserRealmService.getInstance().getAuthorizationManager();

        AuthorizationStore authorizationStore = new InMemoryAuthorizationStore();
        AuthorizationStoreManager.getInstance().addAuthorizationStore("PRIMARY", authorizationStore);
    }

    @Test
    public void testApp() throws UserStoreException {

        configure();

        String userName = "admin";
        String password = "password";
        String userId = null;

        AuthenticationContext context = authManager.authenticate("userName", userName, password);
        if (context.isAuthenticated()) {
            userId = context.getSubject().getUserID();
        }

        Assert.assertTrue(context.isAuthenticated());
        Assert.assertTrue(authzManager.isUserAuthorized(userId, new Permission("/permissions/login")));
    }
}
