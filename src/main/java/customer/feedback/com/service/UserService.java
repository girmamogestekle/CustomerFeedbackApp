package customer.feedback.com.service;

import customer.feedback.com.dto.ResponseHandler;
import customer.feedback.com.dto.UserDto;
import customer.feedback.com.dto.UserRequest;
import customer.feedback.com.dto.UserResponse;
import customer.feedback.com.exception.ResourceNotFoundException;
import customer.feedback.com.mapper.UserMapper;
import customer.feedback.com.model.Role;
import customer.feedback.com.model.UserEntity;
import customer.feedback.com.repository.UserRepository;
import customer.feedback.com.utility.UserAuthentications;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "UserService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @Getter
    @Value("${role.name}")
    private String role;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthentications userAuthentications;
    private final UserMapper userMapper;

    @Transactional
    public void createUser(UserDto userDto, HttpServletRequest httpServletRequest) throws Exception{

        try {
            Set<Role> roles = new HashSet<>();
            roles.add(Role.getRole(role));

            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userDto.setEnabled(true);
            userDto.setRoles(roles);
            userDto.setCreatedDate(new Date());

            UserEntity userEntity = userMapper.mapToUserEntity(userDto);

            userRepository.save(userEntity);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Transactional
    public ResponseEntity<?> updatedUser(UserRequest userRequest, HttpServletRequest httpServletRequest) throws Exception{

        try {
            String username = httpServletRequest.getRemoteUser();
            UserEntity userEntity = getUserEntityByUserName(username);
            String requestUsername = userRequest.getUsername();
            if(!username.equals(requestUsername)){
                if(isUsernameExists(requestUsername)) return ResponseHandler.generateResponse("Username Is Already In Use!!", HttpStatus.BAD_REQUEST, null);
                else userEntity.setUsername(requestUsername);
            }

//            userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userEntity.setPassword(userRequest.getPassword());
            userEntity.setLastModifiedDate(new Date());

            userRepository.save(userEntity);
            return ResponseHandler.generateResponse("User Updated Successfully!", HttpStatus.OK, null);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public void updatedUser(UserEntity userEntity) throws Exception{

        try{
            userRepository.save(userEntity);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Transactional
    public void deletedUser(HttpServletRequest httpServletRequest) throws Exception{

        try{
            userRepository.deleteByUsername(httpServletRequest.getRemoteUser()).orElseThrow(ResourceNotFoundException::new);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    public boolean isUsernameExists(String username) throws Exception{

        try {
            return userRepository.existsByUsername(username);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public UserResponse getUserById(long id) throws Exception{

        try {
            return userMapper.mapToUserResponse(userRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("User", "id", Long.toString(id));
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public UserEntity getUserEntityByUserName(String userName) throws Exception{

        try {
            return userRepository.findByUsername(userName).orElseThrow(ResourceNotFoundException :: new);
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("User", userName, userName);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public UserResponse getUserResponseByUserName(String userName) throws Exception{

        try {
            return userMapper.mapToUserResponse(userRepository.findByUsername(userName).orElseThrow(ResourceNotFoundException :: new));
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("User", userName, userName);
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public UserEntity loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            return userRepository.findByUsernameAndIsEnabled(username,true).orElseThrow(() -> new UsernameNotFoundException("Could not find user name!"));
        } catch (Exception e){
            throw new UsernameNotFoundException("Could not find user name!");
        }

    }

    public List<UserResponse> getEnabledUsers(boolean isEnabled) throws Exception {

        try {
            return userRepository.findAllByIsEnabled(isEnabled).orElseThrow((ResourceNotFoundException :: new))
                    .stream().map(userMapper::mapToUserResponse).toList();
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException("User", Boolean.toString(isEnabled), Boolean.toString(isEnabled));
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public List<UserResponse> getUsers() throws Exception {

        try {
            return userRepository.findAll().stream().map(userMapper::mapToUserResponse).toList();
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

}
