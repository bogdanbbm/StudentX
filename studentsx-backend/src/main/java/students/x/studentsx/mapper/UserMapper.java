package students.x.studentsx.mapper;


import students.x.studentsx.dto.UserDto;
import students.x.studentsx.entity.User;

public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getRole(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public static User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getRole(),
                userDto.getUsername(),
                userDto.getPassword()
        );
    }
}
