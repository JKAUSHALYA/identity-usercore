package org.wso2.carbon.identity.user.core.manager;

import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.UserStoreConstants;
import org.wso2.carbon.identity.user.core.UserStoreException;
import org.wso2.carbon.identity.user.core.config.UserStoreConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class IdentityStoreManager {

    private HashMap<String, UserStore> userStores = new HashMap<String, UserStore>();

    private static IdentityStoreManager identityStoreManager = new IdentityStoreManager();

    public static IdentityStoreManager getInstance() {
        return identityStoreManager;
    }

    IdentityStoreManager(){
        try {
            init();
        } catch (UserStoreException e) {
            e.printStackTrace();
        }
    }



    public void addUserStore(UserStoreConfig userStoreConfig) throws UserStoreException {
        String userStoreName = userStoreConfig.getUserStoreProperties().getProperty(UserStoreConstants.USER_STORE_NAME);
        String userStoreClass = userStoreConfig.getUserStoreProperties().getProperty(UserStoreConstants
                .USER_STORE_CLASS);
        int executionOrder = Integer.parseInt(userStoreConfig.getUserStoreProperties().getProperty
                (UserStoreConstants.EXECUTION_ORDER));
        if (getUserStore(executionOrder) != null) {
            throw new UserStoreException("Error while adding user store. Execution order duplicated");
        }
        if (userStoreName != null && userStoreClass != null) {
            Class clazz = null;
            try {
                clazz = Class.forName(userStoreClass);
                UserStore userStore = (UserStore) clazz.newInstance();
                userStores.put(userStoreName, userStore);
            } catch (ClassNotFoundException e) {
                throw new UserStoreException("Error while initializing user store class " + userStoreClass, e);
            } catch (InstantiationException e) {
                throw new UserStoreException("Error while initializing user store class " + userStoreClass, e);
            } catch (IllegalAccessException e) {
                throw new UserStoreException("Error while initializing user store class " + userStoreClass, e);
            }
        }
    }

    public void removeUserStore(String userStoreName) {
        userStores.remove(userStoreName);
    }

    public UserStore getUserStore(String userStoreName) {
        return userStores.get(userStoreName);
    }

    public UserStore getUserStore(int executionOrder) {
        Iterator it = userStores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            UserStore userStore = userStores.get(pair.getKey());
            if (userStore.getExecutionOrder() == executionOrder) {
                return userStore;
            }
        }
        return null;
    }

    public HashMap<String, UserStore> getUserStores() {
        return this.userStores;
    }

    private void init() throws UserStoreException {
        UserStoreConfig config = new UserStoreConfig();
        config.setUserStoreProperties(readUserStoreConfig("PRIMARY.properties"));
        this.addUserStore(config);
    }

    protected Properties readUserStoreConfig(String userStoreName) throws UserStoreException {
        InputStream input = null;
        Properties prop = new Properties();
        try {
            input = new FileInputStream("/media/hasinthaindrajee/204c7dcd-f122-4bc5-9743-f46bcdf78f37/c5/identity-usercore/realm/src/main/resources/PRIMARY.properties");
            prop.load(input);
        } catch (IOException e) {
            throw new UserStoreException("Error while reading configuration file for user store " + userStoreName, e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    System.out.println("Error while closing input stream ");
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }
}
