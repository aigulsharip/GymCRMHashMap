package kz.group36.springboot.config;

import kz.group36.springboot.entity.User;
import kz.group36.springboot.db.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DataInitializer {

    @Autowired
    private UserStorage userStorage;

    @Value("${data.file.path}")
    private String dataFilePath;


    @PostConstruct
    public void initializeData() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(dataFilePath)) {
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        processUserLine(line);
                    }
                }
            } else {
                System.err.println("File 'file.txt' not found in classpath.");
            }
        } catch (IOException e) {
            System.err.println("Error reading 'file.txt': " + e.getMessage());
        }
    }

    private void processUserLine(String line) {
        // Split the line based on the delimiter (e.g., comma)
        String[] parts = line.split(",");

        if (parts.length == 6) {
            // Assuming the columns are in order: id, firstName, lastName, username, password, isActive
            try {
                Long id = Long.parseLong(parts[0].trim());
                String firstName = parts[1].trim();
                String lastName = parts[2].trim();
                String username = parts[3].trim();
                String password = parts[4].trim();
                boolean isActive = Boolean.parseBoolean(parts[5].trim());

                // Create User object and add it to the UserStorage
                User user = new User(id, firstName, lastName, username, password, isActive);
                userStorage.addUser(user);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing user data: " + line);
            }
        } else {
            System.err.println("Invalid data format: " + line);
        }
    }
}

/*
public class DataInitializer {

    @Value("${data.file.path}")
    private String dataFilePath;

    @Autowired
    private UserStorage userStorage;

    @PostConstruct
    public void initializeData() {
        // Read data from the file and populate the storage
        // Use dataFilePath to locate the file
        // Example: Load data from a file and call userStorage.addUser(user);
    }
}
*/