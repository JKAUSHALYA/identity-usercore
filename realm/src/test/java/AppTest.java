import common.UserRealmService;
import manager.AuthenticationManager;
import manager.AuthorizationManager;

import java.util.Scanner;

/**
 * Created by damith on 2/2/16.
 */
public class AppTest {

    public static void main( String[] args )
    {

        Scanner input = new Scanner(System.in);

        System.out.println("Enter User Name : ");

        String userName = input.next();

        System.out.println(" Enter Password : ");

        String password = input.next();

        UserRealmService realmService = new UserRealmService();

        AuthenticationManager authManager = realmService.getAuthenticationManager();

        AuthorizationManager authzManager = realmService.getAuthorizationManager();

        if (authManager.authenticate(userName, password)) {
            System.out.println("Authentication Successful");
            if (authzManager.isUserAuthorized(userName, "/permissions/login") ) {
                System.out.println("User Authorized, Login successful!");
            } else {
                System.out.println("user not allowed to login!");
            }
        } else {
            System.out.println("Authentication failed");
        }
    }
}
