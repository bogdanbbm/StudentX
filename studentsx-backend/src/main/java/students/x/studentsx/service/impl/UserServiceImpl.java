package students.x.studentsx.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import students.x.studentsx.dto.UserDto;
import students.x.studentsx.entity.User;
import students.x.studentsx.exception.CustomException;
import students.x.studentsx.exception.ResourceNotFoundException;
import students.x.studentsx.mapper.UserMapper;
import students.x.studentsx.repository.UserRepository;
import students.x.studentsx.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (this.checkUsername(userDto.getUsername())) {
            throw new CustomException("User with username '" + userDto.getUsername() + "' already exists.", "409");
        }

        if (userDto.getRole() == null) {
            throw new CustomException("Role cannot be null.", "400");
        }

        if (userDto.getRole().equalsIgnoreCase("STUDENT") || userDto.getRole().equalsIgnoreCase("TEACHER") || userDto.getRole().equalsIgnoreCase("ADMIN")) {
            userDto.setRole(userDto.getRole().toUpperCase());
        } else {
            throw new CustomException("Role must be STUDENT, TEACHER or ADMIN.", "400");
        }

        User user = UserMapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public Boolean checkUsername(String username) {
        List<User> users = userRepository.findAll();
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User with id '" + id + "' not found.", "404"));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not with given id: " + id));

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        User updatedUser = userRepository.save(user);

        return UserMapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found with given id: " + id, "404"));
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        List<User> users = userRepository.findAll();
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return UserMapper.mapToUserDto(user);
            }
        }
        throw new CustomException("User not found with given username: " + username, "404");
    }
}
