package org.wso2.carbon.identity.user.core.stores;


import org.wso2.carbon.identity.user.core.manager.PersistenceManager;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;

/**
 * Created by damith on 2/3/16.
 */
public abstract class AbstractUserStore extends PersistenceManager {

    

    public boolean addUser(IdentityObject user) {
        persistUser(user);
        return true;
    }

    protected abstract void persistUser(IdentityObject user);

    public IdentityObject searchUser(String userID) {
        return retrieveUser("userName", userID);
    }

    public IdentityObject searchUser(String claimAttribute, String value) {
        
        return retrieveUser(claimAttribute, value);
    }

    protected abstract IdentityObject retrieveUser(String claimAttribute, String value);
    
    public UserRole searchRole(String roleName) {
        return retrieveRole(roleName);
    }

    protected abstract UserRole retrieveRole(String roleName);

    public boolean addRole(UserRole role) {
        persistRole(role);
        return true;
    }

    protected abstract void persistRole(UserRole role);

}
