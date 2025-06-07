package com.example.api;
import io.restassured.RestAssured;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.*;


@Epic("Sample API Testing")
@Feature("Sample Feature")
@Story("Basic APi Test")
@Owner("Advait Katikar")
@Severity(SeverityLevel.CRITICAL)
public class SampleApiTest {
    @BeforeAll
    public static void setup(){
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    @Description("Verify the Get request for all posts returns 200")
    public void testGetPosts(){
        given().log().all()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .body("[0].userId", notNullValue());
    }

    @Test
    @Description("Verify the Get request for single posts returns 200")
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
    @Description("Verify the POST request returns 201")
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
