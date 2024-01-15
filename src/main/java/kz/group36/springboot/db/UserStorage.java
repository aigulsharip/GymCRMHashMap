package kz.group36.springboot.db;


import kz.group36.springboot.entity.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UserStorage {

    private final Map<Long, User> userMap = new HashMap<>();

    private static Long id = 1L;


    public void addUser(User user) {
        user.setId(id);
        user.setUsername(calculateUsername(user.getFirstName(), user.getLastName()));
        user.setPassword(generatePassword());
        user.setIsActive(true);
        userMap.put(user.getId(), user);
        id++;
    }

    public User getUser(Long userId) {
        return userMap.get(userId);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userMap.values());
    }

    public void updateUser(User user) {
        userMap.put(user.getId(), user);
    }

    public void deleteUser(Long userId) {
        userMap.remove(userId);
    }

    private String calculateUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String calculatedUsername = baseUsername;
        int counter = 1;

        while (isUsernameExists(calculatedUsername)) {
            calculatedUsername = baseUsername + counter++;
        }
        return calculatedUsername;
    }

    private boolean isUsernameExists(String username) {
        // Implement logic to check if the username already exists in the storage
        return userMap.values().stream().anyMatch(user -> user.getUsername().equalsIgnoreCase(username));
    }

    private String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int passwordLength = 10;

        return java.util.UUID.randomUUID().toString().substring(0, passwordLength);
    }

}
