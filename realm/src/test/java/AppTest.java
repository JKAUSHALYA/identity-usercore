import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wso2.carbon.identity.user.core.UserStore;
import org.wso2.carbon.identity.user.core.exception.UserStoreException;
import org.wso2.carbon.identity.user.core.common.BasicUserRealmService;
import org.wso2.carbon.identity.user.core.context.AuthenticationContext;
import org.wso2.carbon.identity.user.core.inmemory.InMemoryReadOnlyUserStore;
import org.wso2.carbon.identity.user.core.manager.AuthenticationManager;
import org.wso2.carbon.identity.user.core.manager.AuthorizationManager;

import java.util.HashMap;

/**
 * Main test class.
 */
public class AppTest extends TestCase {

    private static final Logger log = LoggerFactory.getLogger(AppTest.class);

    public AppTest(String testName) {
        super(testName);
    }

    public static Test suite() {

        return new TestSuite(AppTest.class);
    }

    public static void configure() throws UserStoreException {
        InMemoryReadOnlyUserStore store = new InMemoryReadOnlyUserStore();
        HashMap<String, UserStore> stores = new HashMap<String, UserStore>();
        stores.put("PRIMARY", store);
    }

    public void testApp()  throws UserStoreException{

        configure();

        String userName = "admin";
        String password = "password";

        AuthenticationManager authManager = BasicUserRealmService.getInstance().getAuthenticationManager();
        AuthorizationManager authzManager = BasicUserRealmService.getInstance().getAuthorizationManager();
        AuthenticationContext context = authManager.authenticate(userName, password);

        System.out.println("===========================1");
        System.out.println(context.isAuthenticated());
        System.out.println("===========================2");
        System.out.println(authzManager.isUserAuthorized(userName, "/permissions/login"));
    }
}
