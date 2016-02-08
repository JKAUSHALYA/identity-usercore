import common.UserRealmService;
import context.AuthenticationContext;
import impl.InMemoryUserStore;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import manager.AuthenticationManager;
import manager.AuthorizationManager;
import org.junit.Assert;
import principal.IdentityObject;
import stores.AbstractUserStore;
import stores.UserRole;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main test class.
 */
public class AppTest extends TestCase {

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {

        return new TestSuite(AppTest.class);
    }

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

    public static void testApp() {

        configure();

        String userName = "admin";
        String password = "password";

        AuthenticationManager authManager = UserRealmService.getInstance().getAuthenticationManager();
        AuthorizationManager authzManager = UserRealmService.getInstance().getAuthorizationManager();
        AuthenticationContext context = authManager.authenticate( userName, password);

        Assert.assertTrue(context.isAuthenticated());
        Assert.assertTrue(authzManager.isUserAuthorized(userName, "/permissions/login") );
    }
}
