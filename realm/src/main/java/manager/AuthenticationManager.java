package manager;

import common.UserRealmService;
import principal.PrincipalObject;
import stores.AbstractUserStore;

/**
 * Created by damith on 2/2/16.
 */
public class AuthenticationManager extends PersistenceManager {



    private String user1 = "admin";
    private String password1 = "password";

    public AuthenticationManager() {
    }

    public boolean authenticate (String claimAttribute, String claimValue, Object credential) {
        //TODO: create authentication context with user, user store and other information
//        if (claimAttribute.equals("userName")) {
//            if (claimValue.indexOf("/") > 0) {
//                String userName = claimValue.substring(claimValue.indexOf("/"));
//                PrincipalObject user = UserRealmService.getIdentityManager().getUserStores().get(claimValue.substring(claimValue
//                        .indexOf("/"))).searchUser
//                        (claimAttribute, userName);
//
//                return user.getPassword().equals(credential);
//            } else {
//                for (AbstractUserStore store : UserRealmService.getIdentityManager().getUserStores().values()) {
//                    try {
//                        if (store.searchUser(claimAttribute, claimValue).getPassword().equals(credential)){
//                            return true;
//                        }
//                    } catch (Exception e) {
//                        continue;
//                    }
//                }
//            }
//        }
//        return false;
        return UserRealmService.getIdentityManager().authenticate(claimAttribute, claimValue, credential);
    }

}
