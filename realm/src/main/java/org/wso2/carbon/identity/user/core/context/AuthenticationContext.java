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

package org.wso2.carbon.identity.user.core.context;

import org.wso2.carbon.identity.user.core.principal.User;

import java.util.HashMap;
import java.util.Map;

/**
 * AuthenticationContext
 */
public class AuthenticationContext {

    private User user;

    private Map<Object, Object> properties = new HashMap<>();

    public void addProperty(Object key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(Object key) {
        return properties.get(key);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User subject) {
        this.user = subject;
    }
}
