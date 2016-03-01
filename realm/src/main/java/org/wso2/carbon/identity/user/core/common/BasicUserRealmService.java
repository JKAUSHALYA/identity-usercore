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

package org.wso2.carbon.identity.user.core.common;

import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.VirtualAuthorizationStore;
import org.wso2.carbon.identity.user.core.manager.VirtualIdentityStore;
import org.wso2.carbon.identity.user.core.service.UserRealmService;

/**
 * Basic user realm service.
 */
public class BasicUserRealmService implements UserRealmService {

    private static BasicUserRealmService instance = new BasicUserRealmService();
    private AuthenticationManager authenticationManager;
    private VirtualAuthorizationStore virtualAuthorizationStore;
    private VirtualIdentityStore virtualIdentityStore;

    private BasicUserRealmService() {

        authenticationManager = new AuthenticationManager();
        virtualAuthorizationStore = new VirtualAuthorizationStore();
        virtualIdentityStore = new VirtualIdentityStore();
    }

    public static UserRealmService getInstance() {
        return instance;
    }

    @Override
    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    @Override
    public VirtualAuthorizationStore getVirtualAuthorizationStore() {
        return virtualAuthorizationStore;
    }

    @Override
    public VirtualIdentityStore getVirtualIdentityStore() {
        return virtualIdentityStore;
    }

}
