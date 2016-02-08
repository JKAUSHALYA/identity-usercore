

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.common.UserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.impl.InMemoryUserStore;
import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;
import org.wso2.carbon.identity.user.core.principal.IdentityObject;
import org.wso2.carbon.identity.user.core.stores.AbstractUserStore;
import org.wso2.carbon.identity.user.core.stores.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by damith on 2/2/16.
 */
public class AppTest {
    private static final Logger log = LoggerFactory.getLogger(AppTest.class);

    public static void configure() {
        InMemoryUserStore store = new InMemoryUserStore();
        IdentityObject user = new IdentityObject();

        user.setUserName("admin");
        user.setPassword("password");
        user.addRole("ADMIN");

        UserRole role = new UserRole();
        role.setRoleName("ADMIN");
        ArrayList<String> permissions = new ArrayList<String>();
        permissions.add("/permissions/login");
        role.setPermissions(permissions);
        ArrayList<String> members = new ArrayList<String>();
        members.add("admin");
        role.setMembers(members);

        store.addUser(user);
        store.addRole(role);

        HashMap<String, AbstractUserStore> stores = new HashMap<String, AbstractUserStore>();

        stores.put("PRIMARY", store);
        UserRealmService.getInstance().getIdentityManager().setUserStores(stores);
    }

    public static void main(String[] args) {

        configure();
        Scanner input = new Scanner(System.in);

        log.info("Enter User Name : ");

        String userName = input.next();

        log.info(" Enter Password : ");

        String password = input.next();


        AuthenticationManager authManager = UserRealmService.getInstance().getAuthenticationManager();

        AuthorizationManager authzManager = UserRealmService.getInstance().getAuthorizationManager();
        AuthenticationContext context = authManager.authenticate(userName, password);
        if (context.isAuthenticated()) {
            log.info("Authentication Successful");
            if (authzManager.isUserAuthorized(userName, "/permissions/login")) {
               log.info("User Authorized, Login successful!");
            } else {
                log.info("user not allowed to login!");
            }
        } else {
            log.info("Authentication failed");
            context.getCauseOfFailure().printStackTrace();
        }
    }
}
