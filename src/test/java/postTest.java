import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Post;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.requestSpecs;
import specifications.responseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
public class postTest extends baseTest{
    private static String resourcePath = "";
    private static Integer created_Post=0;
    @BeforeGroups("create_post")
    public void createPost(){
        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());
        Response response = given()
                .spec(requestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath + "/v1/post");
        JsonPath jsonPathEvaluator = response.jsonPath();
        created_Post = jsonPathEvaluator.get("id");
    }

    @Test
    public void Test_CreatePost_success(){
        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());
        given()
                .spec(requestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath + "/v1/post")
                .then().statusCode(200).spec(responseSpecs.defaultSpec());
    }
    @Test
    public void Test_Invalid_Token_Cant_Create_New_Post(){

        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());

        given()
                .spec(requestSpecs.generateFakeToken())
                .body(testPost)
                .post(resourcePath+ "/v1/post")
                .then()
                .statusCode(401)
                .spec(responseSpecs.defaultSpec());
    }

    @Test(groups="create_post")
    public void Test_validateAll_posts_Schema(){
        Response response = given()
                .spec(requestSpecs.generateToken())
                .get(resourcePath + "/v1/posts");
              assertThat(response.asString(), matchesJsonSchemaInClasspath("Posts.schema.json"));
    }
    @Test
    public void Test_validate_try_to_list_posts_no_login(){
        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());

        given()
                .spec(requestSpecs.generateFakeToken())
                .body(testPost)
                .get(resourcePath+ "/v1/posts")
                .then()
                .statusCode(401)
                .spec(responseSpecs.defaultSpec());

    }

    @Test(groups="create_post")
    public void A2Test_validate_Specific_Post(){
     given()
                .spec(requestSpecs.generateToken())
                .get( resourcePath +"/v1/post/" + created_Post.toString())
            .then()
                .statusCode(200)
                .spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_post")
    public void Test_try_to_list_nonexistent_Post(){
        given()
                .spec(requestSpecs.generateToken())
                .get( resourcePath +"/v1/post/" + "999999")
                .then()
                .statusCode(404)
                .spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_post")
    public void A1Test_update_Post(){
        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());
        given()
                .spec(requestSpecs.generateToken())
                .body(testPost)
                .put( resourcePath +"/v1/post/" + created_Post.toString())
                .then()
                .statusCode(200)
                .spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_post")
    public void Test_try_to_update_nonexistent_Post(){
        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());
        given()
                .spec(requestSpecs.generateToken())
                .body(testPost)
                .put( resourcePath +"/v1/post/" + "999999")
                .then()
                .statusCode(406)
                .spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_post")
    public void Test_delete_Post(){
        Post testPost = new Post(DataHelper.generate_title(),DataHelper.generate_content());
        given()
                .spec(requestSpecs.generateToken())
                .delete( resourcePath +"/v1/post/" + created_Post.toString())
                .then()
                .statusCode(200)
                .spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_post")
    public void Test_try_to_delete_nonexistent_Post() {
        Post testPost = new Post(DataHelper.generate_title(), DataHelper.generate_content());
        given()
                .spec(requestSpecs.generateToken())
                .body(testPost)
                .delete(resourcePath + "/v1/post/" + "99999")
                .then()
                .statusCode(406)
                .spec(responseSpecs.defaultSpec());

    }

}
