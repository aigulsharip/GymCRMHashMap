package kz.group36.springboot.service;

import kz.group36.springboot.dto.UserDTO;
import kz.group36.springboot.db.UserStorage;
import kz.group36.springboot.entity.User;
import kz.group36.springboot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserStorage userStorage;

    private final UserMapper userMapper;

    public UserService(UserStorage userStorage, UserMapper userMapper) {
        this.userStorage = userStorage;
        this.userMapper = userMapper;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        userStorage.addUser(user);
        return userMapper.toDto(user);
    }

    public UserDTO getUser(Long userId) {
        User user = userStorage.getUser(userId);
        return userMapper.toDto(user);
    }

    public List<UserDTO> getAllUsers() {
        return userStorage.getAllUsers()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDTO updateUser(Long userId, UserDTO updatedUserDTO) {
        User existingUser = userStorage.getUser(userId);
        if (existingUser != null) {
            User updatedUser = userMapper.toEntity(updatedUserDTO);
            // Update properties based on your needs
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setIsActive(updatedUser.getIsActive());
            return userMapper.toDto(existingUser);
        }
        return null; // or throw an exception if not found
    }

    public void deleteUser(Long userId) {
        userStorage.deleteUser(userId);
    }

    private String calculateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String calculatedUsername = baseUsername;
        int counter = 1;

//        while (userRepository.existsByUsername(calculatedUsername)) {
//            calculatedUsername = baseUsername + counter++;
//        }
        return calculatedUsername;
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}

