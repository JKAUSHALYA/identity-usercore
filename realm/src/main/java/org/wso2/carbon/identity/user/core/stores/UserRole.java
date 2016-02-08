package org.wso2.carbon.identity.user.core.stores;

import java.util.ArrayList;

/**
 * Created by damith on 2/3/16.
 */
public class UserRole {
    private String roleName;
    private ArrayList<String> permissions;
    private ArrayList<String> members;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public ArrayList<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
}
