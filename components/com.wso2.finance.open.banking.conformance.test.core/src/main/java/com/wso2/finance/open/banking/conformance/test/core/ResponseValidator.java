package com.wso2.finance.open.banking.conformance.test.core;

import com.atlassian.oai.validator.restassured.SwaggerValidationFilter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static io.restassured.RestAssured.given;
import io.restassured.http.Method;
import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;

public class ResponseValidator {

    private static final String SWAGGER_JSON_URL = "./Swagger/swagger.json";
//    private static final String SWAGGER_JSON_URL = "http://petstore.swagger.io/v2/swagger.json";

    private final SwaggerValidationFilter validationFilter = new SwaggerValidationFilter(SWAGGER_JSON_URL);

    @Test
    public void testGetBranches() {
        given().
                filter(validationFilter).
                baseUri("https://api-openbanking.wso2.com/OpenBankAPI/v1.0.0").
//                basePath("/OpenBankAPI/v1.0.0").
                header("content-type", "application/json").
        when().
                get("/banks/bank-4020-01/branches").
        then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void testGetAtms() {
        given().
                filter(validationFilter).
                baseUri("https://api-openbanking.wso2.com/OpenBankAPI/v1.0.0/").
                header("content-type", "application/json").
        when().
                get("banks/bank-4020-01/atms").
        then().
                assertThat().
                statusCode(200);
    }

//    @Test
//    public void testGetWithInvalidId() {
//        given().
//                filter(validationFilter).
//        when().
//                get("/pet/fido").
//        then().
//                assertThat().
//                statusCode(200);
//    }

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
