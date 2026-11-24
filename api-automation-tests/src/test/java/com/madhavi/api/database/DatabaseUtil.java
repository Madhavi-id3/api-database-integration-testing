
package com.madhavi.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.madhavi.api.utils.ConfigReader;

public class DatabaseUtil {

    // Read database configuration
    private static final String DB_URL =
            ConfigReader.getProperty("dbUrl");

    private static final String DB_USERNAME =
            ConfigReader.getProperty("dbUsername");

    private static final String DB_PASSWORD_ENV =
            ConfigReader.getProperty("dbPasswordEnv");


    // 1. Establish database connection
    public static Connection getConnection() throws SQLException {

        String password = System.getenv(DB_PASSWORD_ENV);

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    DB_PASSWORD_ENV
                    + " environment variable is not configured.");
        }

        return DriverManager.getConnection(
                DB_URL, DB_USERNAME, password);
    }


    // 2. Check user existence using email
    public static boolean isUserPresent(String email)
            throws SQLException {

        String query =
                "SELECT COUNT(*) FROM users WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }

        return false;
    }


    // 3. Retrieve email using user ID
    public static String getUserEmailById(long userId)
            throws SQLException {

        String query =
                "SELECT email FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        }

        return null;
    }


    // 4. Check user existence using ID
    public static boolean isUserPresentById(long userId)
            throws SQLException {

        String query =
                "SELECT COUNT(*) FROM users WHERE id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setLong(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }

        return false;
    }


    // 5. Verify updated user details
    public static boolean verifyUserDetails(
            String email,
            String expectedLastName,
            String expectedPhone)
            throws SQLException {

        String query =
                "SELECT last_name, phone FROM users WHERE email = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query)) {

            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String actualLastName =
                            resultSet.getString("last_name");

                    String actualPhone =
                            resultSet.getString("phone");

                    return expectedLastName.equals(actualLastName)
                            && expectedPhone.equals(actualPhone);
                }
            }
        }

        return false;
    }


    // 6. Get total user count
    public static int getUserCount() throws SQLException {

        String query = "SELECT COUNT(*) FROM users";

        try (Connection connection = getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }

        return 0;
    }

}
