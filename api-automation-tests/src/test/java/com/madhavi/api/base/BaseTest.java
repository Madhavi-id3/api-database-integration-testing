
package com.madhavi.api.base;

import org.testng.annotations.BeforeClass;

import com.madhavi.api.utils.ConfigReader;
import com.madhavi.api.utils.LogUtil;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class BaseTest {

    @BeforeClass
    public void setup() {

        RestAssured.baseURI =
                ConfigReader.getProperty("baseUrl");

        RestAssured.requestSpecification =
                new RequestSpecBuilder()
                        .setContentType(ContentType.JSON)
                        .build();

        LogUtil.info("==================================");
        LogUtil.info("API Automation Framework Started");
        LogUtil.info("Base URI: " + RestAssured.baseURI);
        LogUtil.info("==================================");
    }
}
