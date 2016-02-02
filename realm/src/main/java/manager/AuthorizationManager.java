package manager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damith on 2/2/16.
 */
public class AuthorizationManager extends PersistenceManager {

    Map<String, String[]> permissionsOfRoles;

    public AuthorizationManager() {
        permissionsOfRoles = new HashMap<String, String[]>();
        permissionsOfRoles.put("ADMIN", new String [] {"/permissions/login"});
    }

    public boolean isUserAuthorized(String user, String permission) {
        return false; // implement code here
    }

}
