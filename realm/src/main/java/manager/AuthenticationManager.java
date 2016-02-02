package manager;

import principal.PrincipalObject;

/**
 * Created by damith on 2/2/16.
 */
public class AuthenticationManager extends PersistenceManager {


    private IdentityManager identityManager;

    private String user1 = "admin";
    private String password1 = "password";

    public AuthenticationManager() {
        identityManager = new IdentityManager();
    }

    public boolean authenticate(String username, Object password) {

        PrincipalObject userDetails = identityManager.getPrincipalByUserName(username);

        Object userPassword = userDetails.getPassword();

        if ( password.equals(userPassword) ) {
            return true;
        }
        return false;
    }

}
