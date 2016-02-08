package stores;

import manager.PersistenceManager;
import principal.IdentityObject;

/**
 * Created by damith on 2/3/16.
 */
public abstract class AbstractUserStore extends PersistenceManager {

    

    public boolean addUser(IdentityObject user) {
        persistUser(user);
        return true;
    }
    
    abstract protected void persistUser(IdentityObject user);

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

    abstract protected  void persistRole(UserRole role);

}
