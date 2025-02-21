package customer.feedback.com.dto;

import customer.feedback.com.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private boolean isEnabled;
    private Set<Role> roles;

}
