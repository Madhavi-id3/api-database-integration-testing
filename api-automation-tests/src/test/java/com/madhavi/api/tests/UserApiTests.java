
package com.madhavi.api.tests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.madhavi.api.base.BaseTest;
import com.madhavi.api.database.DatabaseUtil;
import com.madhavi.api.utils.LogUtil;
import com.madhavi.api.utils.TestDataUtil;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApiTests extends BaseTest {

    private static long userId;
    private static String userEmail;

    // 1. CREATE USER - POST

    @Test(priority = 1)
    public void createUserTest() throws Exception {

        LogUtil.info("Starting Create User API Test");

        userEmail = TestDataUtil.generateUniqueEmail();

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("firstName", TestDataUtil.getFirstName());
        requestBody.put("lastName", TestDataUtil.getLastName());
        requestBody.put("email", userEmail);
        requestBody.put("phone", TestDataUtil.getPhone());
        requestBody.put("status", "ACTIVE");

        Response response =
                given()
                    .body(requestBody)
                .when()
                    .post("/api/users");

        Assert.assertEquals(response.getStatusCode(), 201);

        userId = response.jsonPath().getLong("id");

        Assert.assertTrue(userId > 0,
                "Created user ID should be greater than zero");

        Assert.assertTrue(DatabaseUtil.isUserPresent(userEmail),
                "Created user should exist in MySQL");

        Assert.assertTrue(DatabaseUtil.isUserPresentById(userId),
                "Created user ID should exist in MySQL");

        LogUtil.info("User created successfully. Email: " + userEmail);
        LogUtil.info("Created User ID: " + userId);
    }


    // 2. GET USER BY ID - GET

    @Test(priority = 2, dependsOnMethods = "createUserTest")
    public void getUserByIdTest() {

        LogUtil.info("Starting Get User By ID Test");

        Response response =
                given()
                    .pathParam("id", userId)
                .when()
                    .get("/api/users/{id}");

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getLong("id"),
                userId);

        Assert.assertEquals(
                response.jsonPath().getString("email"),
                userEmail);

        LogUtil.info("User retrieved successfully. ID: " + userId);
    }


    // 3. GET ALL USERS - GET

    @Test(priority = 3, dependsOnMethods = "createUserTest")
    public void getAllUsersTest() {

        LogUtil.info("Starting Get All Users Test");

        Response response =
                given()
                .when()
                    .get("/api/users");

        Assert.assertEquals(response.getStatusCode(), 200);

        List<String> emails =
                response.jsonPath().getList("email");

        Assert.assertTrue(
                emails.contains(userEmail),
                "Created user should be present in the user list");

        LogUtil.info("All users retrieved successfully");
    }


    // 4. UPDATE USER - PUT

    @Test(priority = 4, dependsOnMethods = "createUserTest")
    public void updateUserTest() throws Exception {

        LogUtil.info("Starting Update User Test");

        String updatedPhone = TestDataUtil.getUpdatedPhone();

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("firstName", TestDataUtil.getFirstName());
        requestBody.put("lastName", "Vempati");
        requestBody.put("email", userEmail);
        requestBody.put("phone", updatedPhone);
        requestBody.put("status", "ACTIVE");

        Response response =
                given()
                    .pathParam("id", userId)
                    .body(requestBody)
                .when()
                    .put("/api/users/{id}");

        Assert.assertEquals(response.getStatusCode(), 200);

        Assert.assertEquals(
                response.jsonPath().getString("lastName"),
                "Vempati");

        Assert.assertEquals(
                response.jsonPath().getString("phone"),
                updatedPhone);

        Assert.assertTrue(
                DatabaseUtil.verifyUserDetails(
                        userEmail, "Vempati", updatedPhone),
                "Updated user details should match MySQL records");

        LogUtil.info(
                "User updated successfully in API and Database");
    }


    // 5. DUPLICATE EMAIL - POST

    @Test(priority = 5, dependsOnMethods = "createUserTest")
    public void duplicateEmailTest() {

        LogUtil.info("Starting Duplicate Email Test");

        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("firstName", "Duplicate");
        requestBody.put("lastName", "Tester");
        requestBody.put("email", userEmail);
        requestBody.put("phone", "9876543210");
        requestBody.put("status", "ACTIVE");

        Response response =
                given()
                    .body(requestBody)
                .when()
                    .post("/api/users");

        Assert.assertEquals(
                response.getStatusCode(), 409,
                "Duplicate email should return HTTP 409");

        LogUtil.info("Duplicate email correctly rejected");
    }


    // 6. DELETE USER - DELETE

    @Test(priority = 6, dependsOnMethods = "createUserTest")
    public void deleteUserTest() throws Exception {

        LogUtil.info("Starting Delete User Test");

        Response response =
                given()
                    .pathParam("id", userId)
                .when()
                    .delete("/api/users/{id}");

        Assert.assertEquals(response.getStatusCode(), 204);

        Assert.assertFalse(
                DatabaseUtil.isUserPresent(userEmail),
                "Deleted user email should not exist in MySQL");

        Assert.assertFalse(
                DatabaseUtil.isUserPresentById(userId),
                "Deleted user ID should not exist in MySQL");

        LogUtil.info(
                "User deleted successfully from API and Database");
    }


    // 7. VERIFY DELETED USER - GET

    @Test(
        priority = 7,
        dependsOnMethods = "deleteUserTest"
    )
    public void getDeletedUserTest() {

        LogUtil.info("Starting Get Deleted User Test");

        Response response =
                given()
                    .pathParam("id", userId)
                .when()
                    .get("/api/users/{id}");

        Assert.assertEquals(response.getStatusCode(), 404);

        LogUtil.info("Deleted user correctly returns 404");
    }


    // 8. NONEXISTENT USER - GET

    @Test(priority = 8)
    public void getNonexistentUserTest() {

        LogUtil.info("Starting Nonexistent User Test");

        Response response =
                given()
                    .pathParam("id", 999999999999L)
                .when()
                    .get("/api/users/{id}");

        Assert.assertEquals(response.getStatusCode(), 404);

        LogUtil.info("Nonexistent user correctly returns 404");
    }

}
