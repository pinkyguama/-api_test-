import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class registerTest extends baseTest{
    private static String resourcePath = "";

    @Test
    public void Test_CreateUserAlreadyExists(){
        String jsonBody = "{\n" +
                "    \"name\": \"jesrojas\",\n" +
                "    \"email\": \"jesrojas@hotmail.com\",\n" +
                "    \"password\": \"jesrojas\"\n" +
                "\n" +
                "}";

        given().body(jsonBody)
                .when()
                .post(String.format(resourcePath + "/v1/user/register"))

                .then().body("message", equalTo("User already exists"))
                .statusCode(406);
    }
}
