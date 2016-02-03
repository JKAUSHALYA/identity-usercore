package manager;

import common.UserRealmService;
import principal.PrincipalObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by damith on 2/2/16.
 */
public class AuthorizationManager extends PersistenceManager {

    Map<String, String[]> permissionsOfRoles;

    IdentityManager identityManager;

    public AuthorizationManager() {
        identityManager = new UserRealmService().getIdentityManager();
        permissionsOfRoles = new HashMap<String, String[]>();
        permissionsOfRoles.put("ADMIN", new String [] {"/permissions/login"});
    }

    public boolean isUserAuthorized(String user, String permission) {

        PrincipalObject userObject = identityManager.getPrincipalByUserName(user);
        String[] roles = userObject.getUserRoles();

        for (String role : roles) {
            String [] permissionList = permissionsOfRoles.get(role);
            for (String permissionCheck : permissionList) {
                if (permissionCheck.equals(permission)) {
                    return true;
                }
            }
        }

        return false; // implement code here
    }

}
