package customer.feedback.com.service;

import customer.feedback.com.dto.AuthenticationRequest;
import customer.feedback.com.dto.AuthenticationResponse;
import customer.feedback.com.model.AuthenticationEntity;
import customer.feedback.com.repository.AuthenticationRepository;
import customer.feedback.com.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import customer.feedback.com.exception.ResourceNotFoundException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) throws Exception {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(),
                authenticationRequest.getPassword()
        ));
        String generatedToken = jwtUtil.generateToken(authenticationRequest.getUsername());
        String requestedUsername = authenticationRequest.getUsername();
        if(existsByUsername(requestedUsername)){
            AuthenticationEntity originalAuthenticationEntity = getAuthenticationEntityByUsername(requestedUsername);
            originalAuthenticationEntity.setLastLoginDate(new Date());
            updateAuthentication(originalAuthenticationEntity);
        } else{
            saveAuthentication(AuthenticationEntity.builder().username(requestedUsername).lastLoginDate(new Date()).build());
        }

        return AuthenticationResponse.builder().token(generatedToken).expiresIn(jwtUtil.getTokenExpiration()).build();

    }

    public void saveAuthentication(AuthenticationEntity authenticationEntity) throws Exception{

        try{
            authenticationRepository.save(authenticationEntity);
        }
        catch (Exception e){
            throw new Exception();
        }

    }

    public void updateAuthentication(AuthenticationEntity authenticationEntity) throws Exception{

        try{
            authenticationRepository.save(authenticationEntity);
        } catch (Exception e){
            throw new Exception();
        }

    }

    public boolean existsByUsername(String username) throws Exception{

        try{
            return authenticationRepository.existsByUsername(username);
        } catch (Exception e){
            throw new Exception();
        }

    }

    public AuthenticationEntity getAuthenticationEntityByUsername(String username) throws Exception{
            return authenticationRepository.findByUsername(username).orElseThrow(ResourceNotFoundException::new);
    }



}
