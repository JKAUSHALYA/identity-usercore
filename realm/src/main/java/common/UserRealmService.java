package common;

import manager.AuthenticationManager;
import manager.AuthorizationManager;
import manager.ClaimManager;
import manager.IdentityManager;

/**
 * Created by damith on 2/2/16.
 */
public class UserRealmService {

    private static AuthenticationManager authenticationManager;
    private static AuthorizationManager authorizationManager;
    private static IdentityManager identityManager;

    private static UserRealmService instance;

    public static UserRealmService getInstance() {
        if(instance == null) {
            instance = new UserRealmService();
        }
        return instance;
    }

    private UserRealmService () {
        authenticationManager = new AuthenticationManager();
        authorizationManager = new AuthorizationManager();
        identityManager = new IdentityManager();
    }

    public static AuthenticationManager getAuthenticationManager(){
        return authenticationManager;
    }

    public static AuthorizationManager getAuthorizationManager() {
        return authorizationManager;
    }

    public static ClaimManager getClaimManager () {
        return new ClaimManager();
    }

    public static IdentityManager getIdentityManager() {
        return identityManager;
    }

}
