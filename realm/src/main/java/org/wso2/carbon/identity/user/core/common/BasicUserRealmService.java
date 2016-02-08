package org.wso2.carbon.identity.user.core.common;

import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.manager.ClaimManager;
import org.wso2.carbon.identity.user.core.manager.IdentityManager;
import org.wso2.carbon.identity.user.core.service.UserRealmService;

public class BasicUserRealmService implements UserRealmService{
    private AuthenticationManager authenticationManager;
    private AuthorizationManager authorizationManager;
    private IdentityManager identityManager;

    private static BasicUserRealmService instance = new BasicUserRealmService();

    public static UserRealmService getInstance() {
        return instance;
    }

    private BasicUserRealmService() {
        authenticationManager = new AuthenticationManager();
        authorizationManager = new AuthorizationManager();
        identityManager = new IdentityManager();
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public AuthorizationManager getAuthorizationManager() {
        return authorizationManager;
    }

    public ClaimManager getClaimManager() {
        throw new UnsupportedOperationException("Claim Manager is not available for basic user realm service");
    }

    public IdentityManager getIdentityManager() {
        return identityManager;
    }
}
