package common;

import manager.AuthenticationManager;
import manager.AuthorizationManager;
import manager.ClaimManager;

/**
 * Created by damith on 2/2/16.
 */
public class UserRealmService {

    public AuthenticationManager getAuthenticationManager(){
        return new AuthenticationManager();
    }

    public AuthorizationManager getAuthorizationManager() {
        return new AuthorizationManager();
    }

    public ClaimManager getClaimManager () {
        return new ClaimManager();
    }

}
