package specifications;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;


public class responseSpecs {
    public static ResponseSpecification defaultSpec() {
        // baseRequest = given().headers("","");
        ResponseSpecBuilder responseBuilder = new ResponseSpecBuilder();
        responseBuilder.expectHeader("Content-Type", "application/json; charset=utf-8");

        return responseBuilder.build();
    }
}