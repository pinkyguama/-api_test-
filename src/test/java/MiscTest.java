import org.testng.annotations.Test;
import specifications.responseSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

public class MiscTest extends baseTest{
    private static String resourcePath = "";


    @Test
    public void Test_ping_ENDPOINT(){
        given()
                //.headers("User Agent","PostmanRuntime/7.26.10")
        .when()
                .get(String.format(resourcePath +"/ping"))
        .then()
                .body("response", equalTo("pong"))
                .header("Content-Length",equalTo("50"))
        .and()
                .statusCode(200).spec(responseSpecs.defaultSpec());
}
    @Test
    public void Test_HomePage_Response(){
        given()
                .get(resourcePath)
        .then()
                .body(containsString("Gin Boilerplate"))
                .statusCode(200);
    }
    @Test
    public void Test_HomePage_Respnse(){
        given()
                .get(resourcePath +"/ping")
                .then().body("response", equalTo("pong"))
                .statusCode(200).spec(responseSpecs.defaultSpec());
    }
}