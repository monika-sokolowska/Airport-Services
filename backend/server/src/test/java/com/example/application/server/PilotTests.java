
package com.example.application.server;

import io.restassured.RestAssured;
import io.restassured.response.Response;
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
class PilotTests {


	@Test
	public void shouldReturnCorrectStatusCode() {
		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.post("/pilot/landed/123");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Correct status code returned");
	}



}
