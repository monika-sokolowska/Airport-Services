package com.example.application.server.controller;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT

)
class BoardingServiceControllerTest {



    @Test
    public void shouldReturnInternalError() {
        //given
        RequestSpecification post = RestAssured.given();
        //when
        Response response = post.post("/boarding/finished/123");
        //then
        assertEquals(response.statusCode(), 500);
    }



}