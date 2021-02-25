import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.*;

public class baseTest {
   protected ResponseSpecification headerSpec;
   protected static String baseUrl;
   @Parameters("baseUrl")
   @BeforeClass
    public void setup(@Optional("https://api-coffee-testing.herokuapp.com") String baseUrl ){
       RestAssured.baseURI = baseUrl;


    }

}
