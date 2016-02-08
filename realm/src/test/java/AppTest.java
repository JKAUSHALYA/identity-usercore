
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.wso2.carbon.identity.user.core.UserStore;
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

        HashMap<String, UserStore> stores = new HashMap<String, UserStore>();

        stores.put("PRIMARY", store);
        UserRealmService.getInstance().getIdentityManager().setUserStores(stores);
    }

    public static void testApp() {

        configure();

        String userName = "admin";
        String password = "password";

        AuthenticationManager authManager = UserRealmService.getInstance().getAuthenticationManager();
        AuthorizationManager authzManager = UserRealmService.getInstance().getAuthorizationManager();
        AuthenticationContext context = authManager.authenticate(userName, password);

        Assert.assertTrue(context.isAuthenticated());
        Assert.assertTrue(authzManager.isUserAuthorized(userName, "/permissions/login"));
    }
}
