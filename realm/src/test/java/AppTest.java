import common.UserRealmService;
import manager.AuthenticationManager;

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

        if (authManager.authenticate(userName, password)) {
            System.out.println("Authentication Successful");
        }
    }
}
