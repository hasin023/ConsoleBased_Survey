package TextFileHelper;

import User.User;
import UserManager.UserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler {
    private final String fileName;

    public UserFileHandler(String fileName) {
        this.fileName = fileName;
    }

    public void writeUsersToFile(List<User> users) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (User user : users) {
                String userString = user.getUsername() + "," + user.getPassword();
                writer.write(userString);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.err.println("Error writing user data: " + e.getMessage());
        }
    }

    public List<User> readUsersFromFile(UserFactory userFactory) {
        List<User> users = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    User user = userFactory.createUser(username, password);
                    users.add(user);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading user data: " + e.getMessage());
        }
        return users;
    }

    public boolean fileExists() {
        File file = new File(fileName);
        return file.exists();
    }

    public boolean fileHasContent() {
        File file = new File(fileName);
        return file.length() > 0;
    }

    public List<User> createFile() {
        List<User> users = new ArrayList<>();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.close();
        } catch (IOException e) {
            System.err.println("Error creating user data file: " + e.getMessage());
        }
        return users;
    }
}
