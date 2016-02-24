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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.config.UserStoreConfig;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.stores.UserStore;
import org.wso2.carbon.identity.user.core.stores.UserStoreConstants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Identity store manager.
 */
public class IdentityStoreManager {

    private static Logger log = LoggerFactory.getLogger(PersistenceManager.class);
    private static IdentityStoreManager identityStoreManager = new IdentityStoreManager();
    private HashMap<String, UserStore> userStores = new HashMap<String, UserStore>();

    public IdentityStoreManager() {
        try {
            init();
        } catch (UserStoreException e) {
            log.error("Error while initializing identity store manager");
        }
    }

    public static IdentityStoreManager getInstance() {
        return identityStoreManager;
    }

    public void addUserStore(UserStoreConfig userStoreConfig) throws UserStoreException {
        String userStoreID = userStoreConfig.getUserStoreProperties().getProperty(UserStoreConstants.USER_STORE_ID);
        String userStoreClass = userStoreConfig.getUserStoreProperties().getProperty(UserStoreConstants
                .USER_STORE_CLASS);
        int executionOrder = Integer.parseInt(userStoreConfig.getUserStoreProperties().getProperty
                (UserStoreConstants.EXECUTION_ORDER));
        if (getUserStore(executionOrder) != null) {
            throw new UserStoreException("Error while adding user store. Execution order duplicated");
        }
        if (userStoreID != null && userStoreClass != null) {
            Class clazz = null;
            try {
                clazz = Class.forName(userStoreClass);
                UserStore userStore = (UserStore) clazz.newInstance();
                userStore.init(userStoreConfig);
                userStores.put(userStoreID, userStore);
            } catch (ClassNotFoundException e) {
                throw new UserStoreException("Error while initializing user store class " + userStoreClass, e);
            } catch (InstantiationException e) {
                throw new UserStoreException("Error while instantiating user store class " + userStoreClass, e);
            } catch (IllegalAccessException e) {
                throw new UserStoreException("Error while accessing user store class " + userStoreClass, e);
            }
        }
    }

    public void removeUserStore(String userStoreName) {
        userStores.remove(userStoreName);
    }

    public UserStore getUserStore(String userStoreName) {
        return userStores.get(userStoreName);
    }

    public UserStore getUserStore(int executionOrder) {
        Iterator it = userStores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UserStore userStore = userStores.get(pair.getKey());
            if (userStore.getExecutionOrder() == executionOrder) {
                return userStore;
            }
        }
        return null;
    }

    public HashMap<String, UserStore> getUserStores() {
        return this.userStores;
    }

    private void init() throws UserStoreException {
        UserStoreConfig config = new UserStoreConfig();
        config.setUserStoreProperties(readUserStoreConfig("PRIMARY.properties"));
        this.addUserStore(config);
    }

    protected Properties readUserStoreConfig(String userStoreName) {

        ResourceBundle bundle = ResourceBundle.getBundle("PRIMARY");
        Properties prop = new Properties();

        for (String key : bundle.keySet()) {
            prop.put(key, bundle.getString(key));
        }
        return prop;
    }

    public UserStore getUserStoreFromID(String userStoreId) {
        return userStores.get(userStoreId);
    }
}
