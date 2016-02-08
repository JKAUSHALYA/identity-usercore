package org.wso2.carbon.identity.user.core.principal;

import java.util.ArrayList;

/**
 * Created by damith on 2/2/16.
 */
public class IdentityObject {
    private Object password;
    private String userName;
    private ArrayList<String> memberOf;

    public IdentityObject() {
        memberOf = new ArrayList<String>();
    }
    public IdentityObject(String name) {
        this.userName = name;
    }

    public void setUserName(String name) {
        this.userName = name;
    }

    public Object getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String[] getUserRoles() {
        return new String[]{"ADMIN"};
    }

    public ArrayList<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(ArrayList<String> memberOf) {
        this.memberOf = memberOf;
    }

    public void addRole(String role) {
        memberOf.add(role);
    }

    public void setPassword(Object password) {
        this.password = password;
    }
}
