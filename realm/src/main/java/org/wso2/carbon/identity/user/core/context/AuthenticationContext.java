package org.wso2.carbon.identity.user.core.context;


import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.util.AuthenticationFailure;

/**
 * Created by damith on 2/3/16.
 */
public class AuthenticationContext {
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
