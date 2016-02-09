package org.wso2.carbon.identity.user.core.util;

import org.wso2.carbon.identity.user.core.UserStoreConstants;

public class UserCoreUtil {

    public static final String DOMAIN_SEPARATOR = "/";

    /**
     * Returns only username
     *
     * @param username username with domain name or only username
     * @return
     */
    public String getUserName(String username) {
        if (!(username.indexOf(DOMAIN_SEPARATOR) < 0)) {
            return username.substring(username.indexOf(DOMAIN_SEPARATOR) + 1);
        }
        return username;
    }

    public String getUserStoreName(String username) {
        if (!(username.indexOf(DOMAIN_SEPARATOR) < 0)) {
            return username.substring(0, username.indexOf(DOMAIN_SEPARATOR));
        }
        return UserStoreConstants.PRIMARY;
    }

    public String appendDomainName(String username, String domainName) {
        return domainName + DOMAIN_SEPARATOR + username;
    }
}
