package manager;

import common.UserRealmService;
import stores.UserRole;

import java.util.ArrayList;

/**
 * Created by damith on 2/2/16.
 */
public class AuthorizationManager extends PersistenceManager {


    public AuthorizationManager() {
    }

    public boolean isUserAuthorized(String user, String permission) {

        ArrayList<String> roles = UserRealmService.getIdentityManager().getRolesOfUser(user);

        for (String role : roles) {
            UserRole roleObject = UserRealmService.getIdentityManager().getRole(role);
            ArrayList<String> permissionList = roleObject.getPermissions();
            for (String permissionCheck : permissionList) {
                if (permissionCheck.equals(permission)) {
                    return true;
                }
            }
        }

        return false; // implement code here
    }

}
