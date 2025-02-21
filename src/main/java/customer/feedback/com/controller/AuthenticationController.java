package customer.feedback.com.controller;

import customer.feedback.com.dto.AuthenticationRequest;
import customer.feedback.com.dto.ResponseHandler;
import customer.feedback.com.service.AuthenticationService;
import customer.feedback.com.utility.RestAPIConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "AuthenticationController")
@RequestMapping(value = RestAPIConstants.AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login")
   public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{

        try{

            return ResponseHandler.generateResponse("Token Created Successfully!!!", HttpStatus.OK, authenticationService.login(authenticationRequest));

        } catch (BadCredentialsException e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.UNAUTHORIZED, null);
        }

   }



}
