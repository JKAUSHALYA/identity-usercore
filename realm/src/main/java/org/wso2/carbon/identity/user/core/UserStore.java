package org.wso2.carbon.identity.user.core;

import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.UserRole;

public interface UserStore {

    boolean addUser(IdentityObject user);

    void persistUser(IdentityObject user);

    IdentityObject searchUser(String userID);

    IdentityObject searchUser(String claimAttribute, String value);

    IdentityObject retrieveUser(String claimAttribute, String value);

    UserRole retrieveRole(String roleName);

    boolean addRole(UserRole role);

    void persistRole(UserRole role);

    boolean isReadOnly();

    UserRole searchRole(String roleName);

}
