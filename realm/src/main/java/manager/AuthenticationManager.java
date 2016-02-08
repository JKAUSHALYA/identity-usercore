package manager;

import common.UserRealmService;
import context.AuthenticationContext;

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

        String userID = UserRealmService.getIdentityManager().searchUserFromClaim(authenticatingAttribute, userId);
        return UserRealmService.getIdentityManager().authenticate(userID, credential);
    }

}
