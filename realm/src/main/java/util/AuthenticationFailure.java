package util;

/**
 * Created by damith on 2/3/16.
 */
public class AuthenticationFailure extends Throwable {
    private  Exception cause;

    public AuthenticationFailure(Exception e) {
        this.cause = e;
    }

    @Override
    public Throwable getCause() {
        return this.cause;
    }
}
