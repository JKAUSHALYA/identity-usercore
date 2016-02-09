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

import java.util.ArrayList;

/**
 * IdentityObject
 */
public class IdentityObject {

    private String userName;

    private ArrayList<String> memberOf;

    public IdentityObject() {
        memberOf = new ArrayList<String>();
    }

    public IdentityObject(String name) {
        this.userName = name;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public String getUserName() {
        return userName;
    }

    public String[] getUserRoles() {
        return new String[]{"ADMIN"};
    }

    public ArrayList<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(ArrayList<String> memberOf) {
        this.memberOf = memberOf;
    }

    public void addRole(String role) {
        memberOf.add(role);
    }

}
