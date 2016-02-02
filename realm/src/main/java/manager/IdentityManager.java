package manager;

import principal.PrincipalObject;

/**
 * Created by damith on 2/2/16.
 */
public class IdentityManager extends PersistenceManager {

    PrincipalObject[] principals;

    public IdentityManager() {
        principals = new PrincipalObject[] {new PrincipalObject()};
    }
    public PrincipalObject getPrincipalByUserName(String username) {
        PrincipalObject returnObject = null;
        for (PrincipalObject principal : principals) {
            if (principal.getUserName().equals(username)) {
                returnObject = principal;
            }
        }
        return returnObject;
    }
}
