
package com.madhavi.api.tests;

import java.sql.Connection;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.madhavi.api.database.DatabaseUtil;

public class DatabaseConnectionTest {

    @Test
    public void verifyDatabaseConnection() throws Exception {

        try (Connection connection = DatabaseUtil.getConnection()) {

            Assert.assertNotNull(
                    connection,
                    "MySQL connection should not be null");

            Assert.assertFalse(
                    connection.isClosed(),
                    "MySQL connection should be active");

            System.out.println("================================");
            System.out.println("MYSQL CONNECTION SUCCESSFUL");
            System.out.println("Database: user_management_db");
            System.out.println("Port: 3307");
            System.out.println("================================");
        }
    }
}



