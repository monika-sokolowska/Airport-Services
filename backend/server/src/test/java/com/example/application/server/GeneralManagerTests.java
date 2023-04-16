
package com.example.application.server;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT
)
class GeneralManagerTests {


    @Test
    public void shouldReturnCorrectBody() {
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.post("/pilot/landed/123");

        RequestSpecification getRequest = RestAssured.given();
        Response getResponse = getRequest.get("/generalmanager/getmessage?flightNumber=123");
        ResponseBody responseBody = getResponse.getBody();
        String body = responseBody.asString();
        Assert.assertEquals(body, "{\"message\":\"landed\",\"flightNumber\":123}", "Correct response body returned");
    }


}
