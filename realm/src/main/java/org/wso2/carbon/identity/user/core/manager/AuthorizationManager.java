package org.wso2.carbon.identity.user.core.manager;


import org.wso2.carbon.identity.user.core.common.UserRealmService;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.ArrayList;

/**
 * Created by damith on 2/2/16.
 */
public class AuthorizationManager extends PersistenceManager {


    public AuthorizationManager() {
    }

    public boolean isUserAuthorized(String user, String permission) {

        ArrayList<String> roles = UserRealmService.getInstance().getIdentityManager().getRolesOfUser(user);

        for (String role : roles) {
            UserRole roleObject = UserRealmService.getInstance().getIdentityManager().getRole(role);
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
