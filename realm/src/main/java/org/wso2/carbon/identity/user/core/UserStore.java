package org.wso2.carbon.identity.user.core;

import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.Properties;

public interface UserStore {

    void init(Properties properties) throws UserStoreException ;

    int getExecutionOrder();

    boolean addUser(IdentityObject user) throws UserStoreException;

    void persistUser(IdentityObject user) throws UserStoreException;

    IdentityObject searchUser(String userID) throws UserStoreException;

    IdentityObject searchUser(String claimAttribute, String value) throws UserStoreException;

    IdentityObject retrieveUser(String claimAttribute, String value) throws UserStoreException;

    UserRole retrieveRole(String roleName) throws UserStoreException;

    boolean addRole(UserRole role) throws UserStoreException;

    void persistRole(UserRole role) throws UserStoreException;

    boolean isReadOnly() throws UserStoreException;

    UserRole searchRole(String roleName) throws UserStoreException;

    String getUserStoreName();

    Properties getUserStoreProperties();

}
