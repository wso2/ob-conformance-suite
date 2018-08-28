package com.wso2.finance.open.banking.conformance.test.core;

import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static io.restassured.RestAssured.given;

public class ResponseValidator {
//    private static final String SWAGGER_JSON_URL = "./src/main/resources/Swagger/swagger.json";
    private static final String SWAGGER_JSON_URL = "http://petstore.swagger.io/v2/swagger.json";

    private final SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_URL);

    @Test
    public void testGetValidPet() {
        given().
                filter(validationFilter).
                baseUri("https://petstore.swagger.io/v2").
//                basePath("/OpenBankAPI/v1.0.0").
//                header("content-type", "application/json").
        when().
                get("/store/order/1").
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void testGetInvalidPet() {
        given().
                filter(validationFilter).
        when().
                get("/pet/2").
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void testGetWithInvalidId() {
        given().
                filter(validationFilter).
        when().
                get("/pet/fido").
        then().
                assertThat().
                statusCode(200);
    }

    public void ValidateResponse(){
        Result result = JUnitCore.runClasses(ResponseValidator.class);

        System.out.println("Total Number of tests: ");
        System.out.println(result.getRunCount());

        System.out.println("Total Number of failed tests: ");
        System.out.println(result.getFailureCount());

        for (Failure failure : result.getFailures()) {
            System.out.println("Test : ");
            System.out.println(failure.toString());
        }

        System.out.println("Final");
        System.out.println(result.wasSuccessful());
    }

}
