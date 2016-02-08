package org.wso2.carbon.identity.user.core.common;


import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.manager.ClaimManager;
import org.wso2.carbon.identity.user.core.manager.IdentityManager;

/**
 * Created by damith on 2/2/16.
 */
public class UserRealmService {

    private  AuthenticationManager authenticationManager;
    private  AuthorizationManager authorizationManager;
    private  IdentityManager identityManager;

    private static UserRealmService instance = new UserRealmService();

    public static UserRealmService getInstance() {
        return instance;
    }

    private UserRealmService () {
        authenticationManager = new AuthenticationManager();
        authorizationManager = new AuthorizationManager();
        identityManager = new IdentityManager();
    }

    public  AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public  AuthorizationManager getAuthorizationManager() {
        return authorizationManager;
    }

    public  ClaimManager getClaimManager () {
        return new ClaimManager();
    }

    public  IdentityManager getIdentityManager() {
        return identityManager;
    }

}
