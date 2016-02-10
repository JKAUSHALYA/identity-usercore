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

import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.model.Group;

import java.util.ArrayList;
import java.util.Map;

/**
 * IdentityObject
 */
public class IdentityObject {

    private UserStore userStore;

    private String userID;

    private String userStoreID;

    private Map<String, String> claims;
    private ArrayList<Group> groups;

    public IdentityObject(String userID) {
        this.userID = userID;
        groups = new ArrayList<Group>();
    }

    public Map<String, String> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, String> claims) {
        this.claims = claims;
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

    public UserStore getUserStore() {
        return userStore;
    }

    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    public String[] getUserRoles() {
        return new String[]{"ADMIN"};
    }

    public ArrayList<Group> getMemberOf() {
        return groups;
    }

    public void setMemberOf(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public void addGroup(Group group) {
        this.groups.add(group);
    }
}
