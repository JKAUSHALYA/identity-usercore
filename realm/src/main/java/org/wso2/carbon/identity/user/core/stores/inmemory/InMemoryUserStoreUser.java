package org.wso2.carbon.identity.user.core.stores.inmemory;

import java.util.List;
import java.util.Map;

public class InMemoryUserStoreUser {

    private String userID;
    private Map<String,String> claims;
    private char[] password;
    private List<String> roles;

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Map<String, String> getClaims() {
        return claims;
    }

    public void setClaims(Map<String, String> claims) {
        this.claims = claims;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
