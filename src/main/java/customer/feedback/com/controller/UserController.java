package customer.feedback.com.controller;

import customer.feedback.com.dto.ResponseHandler;
import customer.feedback.com.dto.UserRequest;
import customer.feedback.com.mapper.UserMapper;
import customer.feedback.com.service.UserService;
import customer.feedback.com.utility.RestAPIConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "UserController")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(value = RestAPIConstants.REGISTER)
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest, HttpServletRequest httpServletRequest) throws Exception{

        if(userService.isUsernameExists(userRequest.getUsername())){
            return ResponseEntity.badRequest().body("Error: Username is already in use!");
        }

        userService.createUser(userMapper.mapToUserDto(userRequest), httpServletRequest);

        return ResponseHandler.generateResponse("User Created Successfully!", HttpStatus.OK, null);

    }

    @PutMapping(value = "RestConstants.USER_UPDATE")
    public ResponseEntity<?> updatedUser(@RequestBody UserRequest userRequest, HttpServletRequest httpServletRequest) throws Exception{

        return userService.updatedUser(userRequest, httpServletRequest);

    }

    @DeleteMapping(value = "RestConstants.USER_DELETE")
    public ResponseEntity<?> deletedUser(HttpServletRequest httpServletRequest) throws Exception{

        userService.deletedUser(httpServletRequest);
        return ResponseHandler.generateResponse("User Deleted Successfully!", HttpStatus.OK, null);

    }

    @GetMapping(value = "RestConstants.USER_GET")
    public ResponseEntity<?> getUserById(@RequestParam int id) throws Exception{
        return ResponseHandler.generateResponse("Get User By Id Successfully!", HttpStatus.OK, userService.getUserById(id));
    }

    @GetMapping(value = "RestConstants.USER_GET_BY_USERNAME")
    public ResponseEntity<?> getUserByUsername(@RequestParam String username) throws Exception{
        return ResponseHandler.generateResponse("Get User By User Name Successfully!", HttpStatus.OK, userService.getUserResponseByUserName(username));
    }

    @GetMapping(value = "RestConstants.USER_GETS")
    public ResponseEntity<?> getUsers() throws Exception{
        return ResponseHandler.generateResponse("Get Users Successfully!", HttpStatus.OK, userService.getUsers());
    }

}
