package customer.feedback.com.model;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    ADMIN,
    USER;

    private static Map<String, Role> roleMapping = new HashMap<>();
    static {
        roleMapping.put(ADMIN.name(), Role.ADMIN);
        roleMapping.put(USER.name(), Role.USER);
    }

    public static Role getRole(String role){
        if(roleMapping.get(role) == null)
            throw new RuntimeException(String.format("There is no (%s) name in role"));
        return roleMapping.get(role);
    }

}
