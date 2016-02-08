package org.wso2.carbon.identity.user.core;

public class UserStoreException extends Exception {

    /**
     * Default serial
     */
    private static final long serialVersionUID = -6057036683816666255L;

    public UserStoreException() {
        super();
    }
    public UserStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserStoreException(String message, boolean convertMessage) {
        super(message);
    }


    public UserStoreException(String message) {
        super(message);
    }

    public UserStoreException(Throwable cause) {
        super(cause);
    }

}
