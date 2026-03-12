package com.api.config;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";

    public static RequestSpecification requestSpec() {

        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(BASE_URL) 
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("api_key", "special-key");
        //.log(LogDetail.BODY);

        return builder.build();
    }

    public static ResponseSpecification responseSpec() {
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        builder.expectContentType(ContentType.JSON);

        return builder.build();
    }
}
