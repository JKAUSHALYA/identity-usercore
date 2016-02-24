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
import org.wso2.carbon.identity.user.core.exception.AuthenticationFailure;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.principal.User;

import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;

/**
 * AuthenticationManager
 */
public class AuthenticationManager implements PersistenceManager {

    /**
     * Authenticate a user.
     * @param callbacks Callbacks to get the user information
     * @return Authentication context.
     * @throws UserStoreException
     * @throws AuthenticationFailure
     */
    public AuthenticationContext authenticate(Callback [] callbacks) throws UserStoreException, AuthenticationFailure {

        char [] password = null;
        String username = null;

        for (Callback callback : callbacks) {

            if (callback instanceof NameCallback) {
                username = ((NameCallback) callback).getName();
            } else if (callback instanceof PasswordCallback) {
                password = ((PasswordCallback) callback).getPassword();
            }
        }

        if (username == null) {
            throw new AuthenticationFailure("Username is null");
        }

        List<User> users = BasicUserRealmService.getInstance().getIdentityManager()
                .searchUserFromClaim("username", username);
        AuthenticationContext context = new AuthenticationContext();

        for (User user : users) {
            context = BasicUserRealmService.getInstance().getIdentityManager().authenticate(user.getUserID(), password);
        }

        return context;
    }

    public AuthenticationContext authenticate(String claimAttribute, String value, Object credential) throws
            UserStoreException, AuthenticationFailure {

        List<User> users = BasicUserRealmService.getInstance().getIdentityManager().searchUserFromClaim
                (claimAttribute, value);
        AuthenticationContext context = new AuthenticationContext();

        for (User user : users) {
            context = BasicUserRealmService.getInstance().getIdentityManager().authenticate(user
                    .getUserID(), credential);
        }

        return context;
    }
}
