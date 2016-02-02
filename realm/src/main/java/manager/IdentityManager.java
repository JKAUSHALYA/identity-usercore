package manager;

import principal.PrincipalObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damith on 2/2/16.
 */
public class IdentityManager extends PersistenceManager {

    PrincipalObject[] principals;
    Map<String, String[]> rolesOfPrincipals ;

    public IdentityManager() {
        principals = new PrincipalObject[] {new PrincipalObject()};
        rolesOfPrincipals = new HashMap<String, String[]>();
        rolesOfPrincipals.put(principals[0].getUserName(), new String[]{"ADMIN"});
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

    public String[] getRolesOfPrincipal(String username) {
        return rolesOfPrincipals.get(username);
    }
}
