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

package org.wso2.carbon.identity.user.core.service;

import org.wso2.carbon.identity.user.core.store.AuthorizationStore;
import org.wso2.carbon.identity.user.core.store.CredentialStore;
import org.wso2.carbon.identity.user.core.store.IdentityStore;

/**
 * User realm service.
 */
public interface RealmService {

    /**
     * Get the Authentication store.
     * @return @see CredentialStore
     */
    CredentialStore getCredentialStore();

    /**
     * Get the Virtual authorization store.
     * @return @see AuthorizationStoreConnector
     */
    AuthorizationStore getAuthorizationStore();

    /**
     * Get the Virtual identity store.
     * @return @see IdentityStore
     */
    IdentityStore getIdentityStore();
}