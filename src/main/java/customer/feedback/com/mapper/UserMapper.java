package customer.feedback.com.mapper;

import customer.feedback.com.dto.UserDto;
import customer.feedback.com.dto.UserRequest;
import customer.feedback.com.dto.UserResponse;
import customer.feedback.com.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserResponse mapToUserResponse(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserResponse.class);
    }

    public UserDto mapToUserDto(UserRequest userRequest) {
        return modelMapper.map(userRequest, UserDto.class);
    }

    public UserEntity mapToUserEntity(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }
}
