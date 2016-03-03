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

package org.wso2.carbon.identity.user.core.connector.inmemory;

import org.wso2.carbon.identity.user.core.connector.CredentialStoreConnector;
import org.wso2.carbon.identity.user.core.exception.AuthenticationFailure;
import org.wso2.carbon.identity.user.core.exception.IdentityStoreException;

import javax.security.auth.callback.Callback;

/**
 * In memory connector for the Credential store.
 */
public class InMemoryCredentialStoreConnector implements CredentialStoreConnector {

    @Override
    public String getCredentialStoreId() {
        return null;
    }

    @Override
    public String authenticate(Callback[] callbacks) throws IdentityStoreException, AuthenticationFailure {
        return null;
    }

    @Override
    public boolean canHandle(Callback[] callbacks) {
        return false;
    }
}
