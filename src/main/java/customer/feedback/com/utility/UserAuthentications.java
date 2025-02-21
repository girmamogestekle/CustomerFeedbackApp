package customer.feedback.com.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthentications {

    public String getUserName(){
        Authentication authentication = getAuth();
        return authentication.getName();
    }

    private Authentication getAuth(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
