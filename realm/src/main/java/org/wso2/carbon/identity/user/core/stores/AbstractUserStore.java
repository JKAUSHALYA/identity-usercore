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

package org.wso2.carbon.identity.user.core.stores;

import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.config.UserStoreConfig;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.PersistenceManager;

/**
 * AbstractUserStore
 */
public abstract class AbstractUserStore implements UserStore, PersistenceManager {

    private UserStoreConfig userStoreConfig;

    public void init(UserStoreConfig userStoreConfig) throws UserStoreException {
        if (userStoreConfig != null) {
            this.userStoreConfig = userStoreConfig;
        } else {
            this.userStoreConfig = new UserStoreConfig();
        }
    }

    public UserStoreConfig getUserStoreConfig() {
        return this.userStoreConfig;
    }
}
