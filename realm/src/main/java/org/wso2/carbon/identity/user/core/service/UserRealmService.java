package org.wso2.carbon.identity.user.core.service;

import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.manager.ClaimManager;
import org.wso2.carbon.identity.user.core.manager.IdentityManager;

public interface UserRealmService {

    public AuthenticationManager getAuthenticationManager();

    public AuthorizationManager getAuthorizationManager();

    public ClaimManager getClaimManager();

    public IdentityManager getIdentityManager();
}
