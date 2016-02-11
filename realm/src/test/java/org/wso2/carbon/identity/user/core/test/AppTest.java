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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.common.BasicUserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.stores.inmemory.InMemoryReadOnlyUserStore;

import java.util.HashMap;

/**
 * Main test class.
 */
public class AppTest {

    private static final Logger log = LoggerFactory.getLogger(AppTest.class);

    public static void configure() throws UserStoreException {
        InMemoryReadOnlyUserStore store = new InMemoryReadOnlyUserStore();
        HashMap<String, UserStore> stores = new HashMap<String, UserStore>();
        stores.put("PRIMARY", store);
    }

    @Test
    public void testApp() throws UserStoreException {

        configure();

        String userName = "admin";
        String password = "password";

        AuthenticationManager authManager = BasicUserRealmService.getInstance().getAuthenticationManager();
        AuthorizationManager authzManager = BasicUserRealmService.getInstance().getAuthorizationManager();
        AuthenticationContext context = authManager.authenticate(userName, password);

        Assert.assertTrue(context.isAuthenticated());
        // Assert.assertTrue(authzManager.isUserAuthorized(userName, "/permissions/login"));
    }
}
