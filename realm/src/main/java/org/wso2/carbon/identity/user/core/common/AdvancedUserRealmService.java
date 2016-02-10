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
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.manager.ClaimManager;
import org.wso2.carbon.identity.user.core.manager.IdentityManager;
import org.wso2.carbon.identity.user.core.service.UserRealmService;

/**
 * Advance user realm service
 */
public class AdvancedUserRealmService implements UserRealmService {

    private AuthenticationManager authenticationManager;
    private AuthorizationManager authorizationManager;
    private IdentityManager identityManager;

    private static AdvancedUserRealmService instance = new AdvancedUserRealmService();

    public static AdvancedUserRealmService getInstance() {
        return instance;
    }

    private AdvancedUserRealmService() {

        authenticationManager = new AuthenticationManager();
        authorizationManager = new AuthorizationManager();
        identityManager = new IdentityManager();
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public AuthorizationManager getAuthorizationManager() {
        return authorizationManager;
    }

    public ClaimManager getClaimManager() {
        return new ClaimManager();
    }

    public IdentityManager getIdentityManager() {
        return identityManager;
    }

}
