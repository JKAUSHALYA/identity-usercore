package org.wso2.carbon.identity.user.core.stores;

import org.wso2.carbon.identity.user.core.model.Permission;
import org.wso2.carbon.identity.user.core.model.Role;

import java.util.List;

/**
 * Authorization store.
 */
public interface AuthorizationStore {

    /**
     * Get roles for the user id.
     * @param userId User id of the user.
     * @return Roles associated to the user.
     */
    public List<Role> getRoles(String userId);

    /**
     * Get permissions associated to the role.
     * @param roleName Role name of the required role.
     * @return List of permissions associated to the user.
     */
    public List<Permission> getPermissions(String roleName);
}
