package manager;

import principal.PrincipalObject;
import stores.AbstractUserStore;
import stores.UserRole;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by damith on 2/2/16.
 */
public class IdentityManager extends PersistenceManager {



    private HashMap<String, AbstractUserStore> userStores;

    public IdentityManager() {

    }

    public boolean authenticate (String claimAttribute, String claimValue, Object credential) {
        //TODO: create authentication context with user, user store and other information
        if (claimAttribute.equals("userName")) {
            if (claimValue.indexOf("/") > 0) {
                String userName = claimValue.substring(claimValue.indexOf("/")+1);
                PrincipalObject user = userStores.get(claimValue.substring(0,claimValue.indexOf("/"))).searchUser
                        (claimAttribute, userName);

                return user.getPassword().equals(credential);
            } else {
                for (AbstractUserStore store : userStores.values()) {
                    try {
                        if (store.searchUser(claimAttribute, claimValue).getPassword().equals(credential)){
                            return true;
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    private ArrayList<String> prependStoreName(String storeName, ArrayList<String> nameList) {
        ArrayList<String> temp = new ArrayList<String>();

        for (String name : nameList) {
            temp.add(storeName + "/" + name);
        }

        return temp;
    }
    public ArrayList<String> getRolesOfUser(String username) {
        return prependStoreName(username.substring(0,username.indexOf("/")), userStores.get(username.substring(0,
                username
                .indexOf("/"))).searchUser("userName",username.substring(username.indexOf("/")+1))
                .getMemberOf());
    }

    public UserRole getRole(String roleName){
        return userStores.get(roleName.substring(0, roleName.indexOf("/"))).searchRole(roleName.substring(roleName
                .indexOf("/")+1));
    }

    public HashMap<String, AbstractUserStore> getUserStores() {
        return userStores;
    }

    public void setUserStores(HashMap<String, AbstractUserStore> userStores) {
        this.userStores = userStores;
    }
}
