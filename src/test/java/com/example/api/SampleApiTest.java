package com.example.api;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class SampleApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void testGetPosts(){
        given().log().all()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("[0].userId", notNullValue());
    }

    @Test
    public void testGetSinglePost(){
        given()
                .log().all()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", notNullValue());
    }

    @Test
    public void testCreatePost(){
        String payload = """
                {
                    "title": "foo",
                    "body": "bar",
                    "userId": 2
                }
                """;

        given()
                .header("Content-Type","application/json")
                .and()
                .body(payload)
                .when()
                .post("/posts")
                .then()
                .log().all()
                .statusCode(201)
                .body("title",equalTo("foo"))
                .body("body", equalTo("bar"))
                .body("userId", equalTo(2));
    }

}
