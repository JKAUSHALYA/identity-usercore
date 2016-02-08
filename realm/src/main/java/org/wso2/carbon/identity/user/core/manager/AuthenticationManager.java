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

package org.wso2.carbon.identity.user.core.manager;

import org.wso2.carbon.identity.user.core.common.UserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;

/**
 * AuthenticationManager
 */
public class AuthenticationManager implements PersistenceManager {


    private String authenticatingAttribute = "userName";


    public AuthenticationManager() {
    }

    public AuthenticationManager(String claimAttribute) {
        this.authenticatingAttribute = claimAttribute;
    }

    public AuthenticationContext authenticate(String userId, Object credential) {

        String userID = UserRealmService.getInstance().getIdentityManager().searchUserFromClaim
                (authenticatingAttribute, userId);
        return UserRealmService.getInstance().getIdentityManager().authenticate(userID, credential);
    }

}
