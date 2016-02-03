package principal;

/**
 * Created by damith on 2/2/16.
 */
public class PrincipalObject {
    private Object password = "password";
    String userName = "admin";
    private String[] userRoles;

    public Object getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public String[] getUserRoles() {
        return new String[]{"ADMIN"};
    }
}
