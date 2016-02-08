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
import org.wso2.carbon.identity.user.core.UserStoreException;
import org.wso2.carbon.identity.user.core.manager.PersistenceManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * AbstractUserStore
 */
public abstract class AbstractUserStore implements UserStore, PersistenceManager {

    private Properties userStoreProperties;

    public void init(Properties properties) throws UserStoreException {
        if(properties != null){
            this.userStoreProperties = properties;
        }else {
            this.userStoreProperties = new Properties();
        }
    }

    protected Properties readUserStoreConfig(String userStoreName) throws UserStoreException {
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("/media/hasinthaindrajee/204c7dcd-f122-4bc5-9743-f46bcdf78f37/c5/identity-usercore/realm/src/main/resources/PRIMARY.properties");
            prop.load(input);
        } catch (IOException e) {
            throw new UserStoreException("Error while reading configuration file for user store " + userStoreName, e);
        }finally{
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Error while closing input stream ");
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
    @Override
    public Properties getUserStoreProperties() {
        return userStoreProperties;
    }
}
