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

package org.wso2.carbon.identity.user.core.principal;

import org.wso2.carbon.identity.user.core.common.BasicUserRealmService;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.model.Group;

import java.util.List;
import java.util.Map;

/**
 * IdentityObject
 */
public class IdentityObject {

    private String userID;

    private String userStoreID;

    public IdentityObject(String userID) {
        this.userID = userID;
    }

    public Map<String, String> getClaims() throws UserStoreException {
        return BasicUserRealmService.getInstance().getIdentityManager().getUserClaimValues(this.userID);
    }

    public String getUserStoreID() {
        return userStoreID;
    }

    public void setUserStoreID(String userStoreID) {
        this.userStoreID = userStoreID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<Group> getUserGroups() throws UserStoreException {
        return BasicUserRealmService.getInstance().getIdentityManager().getGroupsOfUser(this.userID);
    }

    public void setClaims(Map<String, String> claims) {
        //TODO: Implement write operations
    }

    public void addGroup(Group group) {
        //TODO: Implement write operations
    }
}
