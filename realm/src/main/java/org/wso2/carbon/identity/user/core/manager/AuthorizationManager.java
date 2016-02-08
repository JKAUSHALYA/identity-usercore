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

import org.wso2.carbon.identity.user.core.UserStoreException;
import org.wso2.carbon.identity.user.core.common.BasicUserRealmService;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.ArrayList;

/**
 * AuthorizationManager
 */
public class AuthorizationManager implements PersistenceManager {


    public AuthorizationManager() {
    }

    public boolean isUserAuthorized(String user, String permission) throws UserStoreException {

        ArrayList<String> roles = BasicUserRealmService.getInstance().getIdentityManager().getRolesOfUser(user);

        for (String role : roles) {
            UserRole roleObject = BasicUserRealmService.getInstance().getIdentityManager().getRole(role);
            ArrayList<String> permissionList = roleObject.getPermissions();
            for (String permissionCheck : permissionList) {
                if (permissionCheck.equals(permission)) {
                    return true;
                }
            }
        }

        return false; // implement code here
    }

}
