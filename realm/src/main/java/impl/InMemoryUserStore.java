package impl;

import principal.PrincipalObject;
import stores.AbstractUserStore;
import stores.UserRole;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damith on 2/3/16.
 */
public class InMemoryUserStore extends AbstractUserStore {
    protected Map<String, PrincipalObject> users;
    protected Map<String, UserRole> roles;
    public InMemoryUserStore() {
        users = new HashMap<String, PrincipalObject>();
        roles = new HashMap<String, UserRole>();
    }
    @Override
    protected void persistUser(PrincipalObject user) {
        users.put(user.getUserName(), user);
    }

    @Override
    protected PrincipalObject retrieveUser(String claimAttribute, String value) {

        if (claimAttribute == "userName") {
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
