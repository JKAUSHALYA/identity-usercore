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

import org.wso2.carbon.identity.user.core.stores.AuthorizationStore;
import org.wso2.carbon.identity.user.core.stores.inmemory.InMemoryAuthorizationStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Authorization Store Manager.
 */
public class AuthorizationStoreManager {

    private Map<String, AuthorizationStore> authorizationStores = new HashMap<>();
    private static AuthorizationStoreManager instance = new AuthorizationStoreManager();

    private AuthorizationStoreManager() {

        super();
        AuthorizationStore authorizationStore = new InMemoryAuthorizationStore();
        addAuthorizationStore("PRIMARY", authorizationStore);
    }

    public static AuthorizationStoreManager getInstance() {
        return instance;
    }

    public void addAuthorizationStore(String storeName, AuthorizationStore authorizationStore) {
        authorizationStores.put(storeName, authorizationStore);
    }

    public Map<String, AuthorizationStore> getAuthorizationStores() {
        return authorizationStores;
    }

    public AuthorizationStore getAuthorizationStore(String storeName) {
        return authorizationStores.get(storeName);
    }
}
