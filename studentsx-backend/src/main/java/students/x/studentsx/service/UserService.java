package students.x.studentsx.service;

import students.x.studentsx.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    Boolean checkUsername(String username);
    List<UserDto> getUsers();
    UserDto getUser(Long id);
    UserDto updateUser(Long id, UserDto userDto);
    void deleteUser(Long id);
    UserDto getUserByUsername(String username);
}
