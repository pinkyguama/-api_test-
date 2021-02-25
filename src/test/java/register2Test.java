import model.User;
import org.testng.annotations.Test;
import static helpers.DataHelper.generate_email;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class register2Test extends baseTest{
    private static String resourcePath = "";

    @Test
    public void Test_CreateUserAlreadyExists(){
        User user = new User("Mauruicio","pindjujsktssuguama@hotmail.com", "123@34P");


        given().body(user)
                .when()
                .post(String.format(resourcePath + "/v1/user/register"))

                .then().body("message", equalTo("Successfully registered"))
                .statusCode(200).spec(headerSpec);
    }

    @Test
    public void RegisterNewUser(){
        User user = new User("Mauruicio",generate_email(), "123@34P");


        given().body(user)
                .when()
                .post(String.format(resourcePath + "/v1/user/register"))

                .then().body("message", equalTo("Successfully registered"))
                .statusCode(200).spec(headerSpec);
    }
    @Test
    public void Test_Login_succesful(){
        User user = new User("Mauruicio","prinddijujjdsktysuguama@hotmail.com", "123@34P");


        given().body(user)
                .when()
                .post(String.format(resourcePath + "/v1/user/register"))

                .then().body("message", equalTo("Successfully registered"))
                .statusCode(200).spec(headerSpec);
    }

}
