package com.techie.microservices.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

	@LocalServerPort
	private Integer port;

	@Before
	void setup(){
		RestAssured.baseURI="http://192.168.88.251:8080";
		RestAssured.port = port;
	}
	@Test
	void shouldCreatedProduct() {
		mongoDBContainer.start();
		String requestBody = """
					{
				     "id":"1",
				     "name":"iPhone 16",
				     "description":"iPhone 16 is a smartphone from Apple",
				     "price":1000
				 
				 }
				""";
		RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/product")
				.then()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name",Matchers.equalTo("iPhone 16"))
				.body("description",Matchers.equalTo("iPhone 16 is a smartphone from Apple"))
				.body("price",Matchers.equalTo(1000));

	}

}
