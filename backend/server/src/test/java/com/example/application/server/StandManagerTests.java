
package com.example.application.server;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
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
class StandManagerTests {


    @Test
    public void shouldReturnCorrectBody() {
        RequestSpecification postRequest = RestAssured.given();
        postRequest.post("/pilot/landed/123");

        RequestSpecification generalManagerGetRequest = RestAssured.given();
        generalManagerGetRequest.get("/generalmanager/getmessage?flightNumber=123");

        RequestSpecification generalManagerPostRequest = RestAssured.given();
        JSONObject requestParams = new JSONObject();
        requestParams.put("flightNumber", "123");
        requestParams.put("message", "message3");
        requestParams.put("minutes", "430");
        generalManagerPostRequest.header("Content-Type", "application/json");
        generalManagerPostRequest.body(requestParams.toJSONString());
        generalManagerPostRequest.post("/generalmanager/finished");

        RequestSpecification standManagerGetRequest = RestAssured.given();
        Response standManagerGetResponse = (Response)standManagerGetRequest.get("/standmanager/getmessage?flightNumber=123");
        ResponseBody responseBody = standManagerGetResponse.getBody();
        String body = responseBody.asString();

        Assert.assertEquals(body, "{\"message\":\"message3\",\"minutes\":430,\"flightNumber\":123}", "Correct response body returned");
    }


}
