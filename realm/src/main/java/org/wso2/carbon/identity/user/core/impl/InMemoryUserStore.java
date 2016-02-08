package org.wso2.carbon.identity.user.core.impl;


import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damith on 2/3/16.
 */
public class InMemoryUserStore extends AbstractUserStore {
    protected Map<String, IdentityObject> users;
    protected Map<String, UserRole> roles;
    public InMemoryUserStore() {
        users = new HashMap<>();
        roles = new HashMap<String, UserRole>();
    }
    @Override
    protected void persistUser(IdentityObject user) {
        users.put(user.getUserName(), user);
    }

    @Override
    protected IdentityObject retrieveUser(String claimAttribute, String value) {

        if ("userName".equalsIgnoreCase(claimAttribute)) {
            return users.get(value);
        }
        //TODO: implement logic to return by other claim values by iterating the valueset of the hashmap
        return null;
    }

    @Override
    protected UserRole retrieveRole(String roleName) {
        return roles.get(roleName);
    }

    @Override
    protected void persistRole(UserRole role) {
        roles.put(role.getRoleName(), role);
    }
}
