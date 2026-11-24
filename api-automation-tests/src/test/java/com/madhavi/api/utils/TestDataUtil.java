
package com.madhavi.api.utils;

import java.time.Instant;

public class TestDataUtil {

    // Generate a unique email for every test execution
    public static String generateUniqueEmail() {

        return "madhavi.qa."
                + Instant.now().toEpochMilli()
                + "@example.com";
    }

    // Return test user's first name
    public static String getFirstName() {
        return "Madhavi";
    }

    // Return test user's last name
    public static String getLastName() {
        return "Tester";
    }

    // Return test user's phone number
    public static String getPhone() {
        return "9876543210";
    }

    // Return updated phone number
    public static String getUpdatedPhone() {
        return "9876543211";
    }
}
