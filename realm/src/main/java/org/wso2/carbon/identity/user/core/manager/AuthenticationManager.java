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

import org.wso2.carbon.identity.user.core.common.BasicUserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;

/**
 * AuthenticationManager
 */
public class AuthenticationManager implements PersistenceManager {

    private String authenticatingAttribute = "userName";

    public AuthenticationManager() {
    }

    /**
     * Initialize an authentication manager.
     * @param claimAttribute claim attribute.
     */
    public AuthenticationManager(String claimAttribute) {
        this.authenticatingAttribute = claimAttribute;
    }

    
    /**
     * Authenticate the user.
     * @param userId
     * @param credential
     * @return @see AuthenticationContext
     * @throws UserStoreException
     */
    public AuthenticationContext authenticate(String userId, Object credential) throws UserStoreException {

        String userID = BasicUserRealmService.getInstance().getIdentityManager().searchUserFromClaim
                (authenticatingAttribute, userName);
        return BasicUserRealmService.getInstance().getIdentityManager().authenticate(userID, credential);
    }

}
