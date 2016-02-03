package context;

import principal.IdentityObject;
import util.AuthenticationFailure;
import util.AuthenticationStatus;

/**
 * Created by damith on 2/3/16.
 */
public class AuthenticationContext {
    IdentityObject[] identityObject;


    public IdentityObject getSubject() {
        return null;
    }


    public boolean isAuthenticated() {
        return false;
    }

    public AuthenticationFailure getCauseOfFailure() {
        return null;
    }



}
