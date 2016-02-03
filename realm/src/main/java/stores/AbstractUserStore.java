package stores;

import manager.PersistenceManager;
import principal.PrincipalObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damith on 2/3/16.
 */
public abstract class AbstractUserStore extends PersistenceManager {

    

    public boolean addUser(PrincipalObject user) {
        persistUser(user);
        return true;
    }
    
    abstract protected void persistUser(PrincipalObject user);

    public PrincipalObject searchUser(String claimAttribute, String value) {
        
        return retrieveUser(claimAttribute, value);
    }

    protected abstract PrincipalObject retrieveUser(String claimAttribute, String value);
    
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
