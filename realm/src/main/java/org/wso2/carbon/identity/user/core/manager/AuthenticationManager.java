package org.wso2.carbon.identity.user.core.manager;

import org.wso2.carbon.identity.user.core.common.UserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;

/**
 * Created by damith on 2/2/16.
 */
public class AuthenticationManager extends PersistenceManager {



    private String authenticatingAttribute = "userName";


    public AuthenticationManager() {
    }

    public AuthenticationManager(String claimAttribute) {
        this.authenticatingAttribute = claimAttribute;
    }

    public AuthenticationContext authenticate(String userId, Object credential) {

        String userID = UserRealmService.getInstance().getIdentityManager().searchUserFromClaim
                (authenticatingAttribute, userId);
        return UserRealmService.getInstance().getIdentityManager().authenticate(userID, credential);
    }

}
