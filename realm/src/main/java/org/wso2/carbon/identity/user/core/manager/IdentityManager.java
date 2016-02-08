package org.wso2.carbon.identity.user.core.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;
import org.wso2.carbon.identity.user.core.stores.UserRole;
import org.wso2.carbon.identity.user.core.util.AuthenticationFailure;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by damith on 2/2/16.
 */
public class IdentityManager extends PersistenceManager {


    private HashMap<String, AbstractUserStore> userStores;
    private static final Logger log = LoggerFactory.getLogger(PersistenceManager.class);

    public IdentityManager() {

    }

    public AuthenticationContext authenticate(Object credential) {
        return null;
    }

    public AuthenticationContext authenticate(String userid, char[] password) {
        return null;
    }


    public AuthenticationContext authenticate(String userid, Object credential) {

        AuthenticationContext context = new AuthenticationContext();
        boolean isAuthenticated = false;
        IdentityObject subject = null;
        try {

            if (!(userid.indexOf("/") < 0)) {
                String userName = userid.substring(userid.indexOf("/") + 1);

                IdentityObject user = userStores.get(userid.substring(0, userid.indexOf("/"))).searchUser
                        (userName);
                isAuthenticated = user.getPassword().equals(credential);
                if (isAuthenticated) {
                    context.setSubject(user);
                }
                //return user.getPassword().equals(credential);
            } else {
                for (AbstractUserStore store : userStores.values()) {
                    if (store.searchUser(userid).getPassword().equals(credential)) {
                        context.setSubject(store.searchUser(userid));
                        isAuthenticated = true;
                    }

                }
            }

            context.setAuthenticated(isAuthenticated);

        } catch (Exception e) {
            context.setFailure(new AuthenticationFailure(e));
        }
        return context;
    }

    public String searchUserFromClaim(String claimAttribute, String claimValue) {
        if (claimAttribute.equals("userName")) {
            if (!(claimValue.indexOf("/") < 0)) {
                String userName = claimValue.substring(claimValue.indexOf("/") + 1);

                IdentityObject user = userStores.get(claimValue.substring(0, claimValue.indexOf("/"))).searchUser
                        (userName);

                return user.getUserName();
            } else {
                for (AbstractUserStore store : userStores.values()) {
                    try {
                        if (store.searchUser(claimValue) != null) {
                            return store.searchUser(claimValue).getUserName();
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return null;
    }

    public AuthenticationContext authenticate(String claimAttribute, String claimValue, Object credential) {
        //TODO: create authentication context with user, user store and other information
        if (claimAttribute.equals("userName")) {
            if (!(claimValue.indexOf("/") < 0)) {

            } else {
                for (AbstractUserStore store : userStores.values()) {
                    try {
                        if (store.searchUser(claimValue).getPassword().equals(credential)) {
                            log.debug("User present");
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return new AuthenticationContext();
    }

    private ArrayList<String> prependStoreName(String storeName, ArrayList<String> nameList) {
        ArrayList<String> temp = new ArrayList<String>();

        for (String name : nameList) {
            temp.add(storeName + "/" + name);
        }

        return temp;
    }

    public ArrayList<String> getRolesOfUser(String username) {
        String storeName = "PRIMARY";
        String userName = username;
        if (!(username.indexOf("/") < 0)) {

            storeName = username.substring(0, username.indexOf("/"));
            userName = username.substring(username.indexOf("/") + 1);
        }
        return prependStoreName(storeName, userStores.get(storeName).searchUser("userName", userName)
                .getMemberOf());
    }

    public UserRole getRole(String roleName) {
        return userStores.get(roleName.substring(0, roleName.indexOf("/"))).searchRole(roleName.substring(roleName
                .indexOf("/") + 1));
    }

    public HashMap<String, AbstractUserStore> getUserStores() {
        return userStores;
    }

    public void setUserStores(HashMap<String, AbstractUserStore> userStores) {
        this.userStores = userStores;
    }
}
