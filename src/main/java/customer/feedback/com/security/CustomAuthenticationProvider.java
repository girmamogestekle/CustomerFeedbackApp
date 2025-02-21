package customer.feedback.com.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        if(authentication.getCredentials() == null ) throw new BadCredentialsException("Bad credentials");
        else if(userDetails.isEnabled()) throw  new DisabledException("Your account is not active!!!");
        else if(!userDetails.isAccountNonLocked()) throw  new LockedException("Your account is locked!!!");

        super.additionalAuthenticationChecks(userDetails, authentication);
    }

}
