package customer.feedback.com.dto;

import customer.feedback.com.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private boolean isEnabled;
    private Set<Role> roles;

}
