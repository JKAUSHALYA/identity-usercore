import common.UserRealmService;
import context.AuthenticationContext;
import impl.InMemoryUserStore;
import manager.AuthenticationManager;
import manager.AuthorizationManager;
import principal.IdentityObject;
import stores.AbstractUserStore;
import stores.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by damith on 2/2/16.
 */
public class AppTest {

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
    public static void main( String[] args )
    {

        configure();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter User Name : ");

        String userName = input.next();

        System.out.println(" Enter Password : ");

        String password = input.next();


        AuthenticationManager authManager = UserRealmService.getInstance().getAuthenticationManager();

        AuthorizationManager authzManager = UserRealmService.getInstance().getAuthorizationManager();
        AuthenticationContext context = authManager.authenticate( userName, password);
        if (context.isAuthenticated()) {
            System.out.println("Authentication Successful");
            if (authzManager.isUserAuthorized(userName, "/permissions/login") ) {
                System.out.println("User Authorized, Login successful!");
            } else {
                System.out.println("user not allowed to login!");
            }
        } else {
            System.out.println("Authentication failed");
            context.getCauseOfFailure().printStackTrace();
        }
    }
}
