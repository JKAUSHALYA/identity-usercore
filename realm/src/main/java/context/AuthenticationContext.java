package context;

import principal.IdentityObject;
import util.AuthenticationFailure;
import util.AuthenticationStatus;

/**
 * Created by damith on 2/3/16.
 */
public class AuthenticationContext {
    IdentityObject[] identityObject;
    private IdentityObject subject;
    private boolean isAuthenticated;
    private AuthenticationFailure failure;


    public IdentityObject getSubject() {
        return subject;
    }


    public void setAuthenticated(boolean authenticated) {
        this.isAuthenticated = authenticated;
    }
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public AuthenticationFailure getCauseOfFailure() {
        if (this.isAuthenticated) {
            return null;
        }
        return failure;
    }


    public void setSubject(IdentityObject subject) {
        this.subject = subject;
    }

    public void setFailure(AuthenticationFailure failure) {
        this.failure = failure;
    }
}
