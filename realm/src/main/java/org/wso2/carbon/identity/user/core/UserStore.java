package org.wso2.carbon.identity.user.core;

import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.Properties;

public interface UserStore {

    void init(Properties properties) throws UserStoreException ;

    boolean authenticate(String userid, Object credential) throws UserStoreException;

    boolean isExistingUser(String userName) throws UserStoreException;

    boolean isExistingRole(String roleName) throws UserStoreException;

    String[] getRoleNames() throws UserStoreException;

    String[] getRoleListOfUser(String userName) throws UserStoreException;

    String[] getUserListOfRole(String roleName) throws UserStoreException;

    String[] listUsers(String filter, int maxItemLimit) throws UserStoreException;

    int getExecutionOrder();

    IdentityObject searchUser(String userID) throws UserStoreException;

    IdentityObject searchUser(String claimAttribute, String value) throws UserStoreException;

    IdentityObject retrieveUser(String claimAttribute, String value) throws UserStoreException;

    UserRole retrieveRole(String roleName) throws UserStoreException;

    void persistRole(UserRole role) throws UserStoreException;

    boolean isReadOnly() throws UserStoreException;

    UserRole searchRole(String roleName) throws UserStoreException;

    String getUserStoreName();

    Properties getUserStoreProperties();

    boolean addRole(UserRole role) throws UserStoreException;

    boolean addUser(IdentityObject user) throws UserStoreException;

    void persistUser(IdentityObject user) throws UserStoreException;

}
