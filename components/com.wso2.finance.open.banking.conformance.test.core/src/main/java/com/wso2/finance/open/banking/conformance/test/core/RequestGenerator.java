package com.wso2.finance.open.banking.conformance.test.core;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
//import io.restassured.builder.RequestSpecBuilder;

public class RequestGenerator {

    public RequestGenerator(){}

    public RequestSpecification createRequest()
    {
        System.out.println("creating request");

        //Create a Request pointing to the Service Endpoint
        RestAssured.baseURI = Context.getInstance().getBaseURL();
        RequestSpecification httpRequest = RestAssured.given().accept("application/json").log().all();

       // RequestSpecification httpRequest = new RequestSpecBuilder().build().baseUri(Context.getInstance().getBaseURL()).accept(ContentType.JSON).contentType(ContentType.JSON).relaxedHTTPSValidation();


        return httpRequest;

    }

}
