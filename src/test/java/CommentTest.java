import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Comment;
import model.Post;
import model.User;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.requestSpecs;
import specifications.responseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CommentTest extends baseTest {
    private static String resourcePath = "";
    private static Integer created_Post = 0;
    private static Integer created_Comment = 0;
    private static String errorSQL = "";

    @BeforeGroups("create_post")
    public void createPost() {
        Post testPost = new Post(DataHelper.generate_title(), DataHelper.generate_content());
        Response response = given()
                .spec(requestSpecs.generateToken())
                .body(testPost)
                .post(resourcePath + "/v1/post");
        JsonPath jsonPathEvaluator = response.jsonPath();
        created_Post = jsonPathEvaluator.get("id");
    }
    @BeforeGroups("create_comment")
    public void createComment(){
        createPost();
        Comment testComment = new Comment(DataHelper.generate_title(),DataHelper.generate_content());
        User data = new DataHelper().getTestAuthUser();
        Response response2 = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .body(testComment)
                .post(resourcePath + "/v1/comment/"+ created_Post);
        JsonPath jsonPathEvaluator2 = response2.jsonPath();
        created_Comment = jsonPathEvaluator2.get("id");
    }

    @Test(groups="create_post")
    public void Test_Create_comment_success() {
        Comment testComment = new Comment(DataHelper.generate_title(),DataHelper.generate_content());
        User data = new DataHelper().getTestAuthUser();
        given().auth()
                .basic(data.getEmail(),data.getPassword())
                .body(testComment)
                .post(resourcePath + "/v1/comment/"+ created_Post)
                .then().statusCode(200).spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_post")
    public void Test_try_to_Create_comment_Unauthorized() {
        Comment testComment = new Comment(DataHelper.generate_title(),DataHelper.generate_content());
        User data = new DataHelper().getTestAuthUser();
        given().auth()
                .basic(data.getEmail(),data.getPassword()+"Un")
                .body(testComment)
                .post(resourcePath + "/v1/comment/"+ created_Post)
                .then().statusCode(401).spec(responseSpecs.defaultSpec());
    }
    @Test(groups="create_comment")
    public void Test_validate_All_Comments() {
        User data = new DataHelper().getTestAuthUser();
        Response response = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .get( resourcePath +"/v1/comments/" + created_Post.toString());
        assertThat(response.asString(), matchesJsonSchemaInClasspath("Comment.schema.json"));
    }
    @Test(groups="create_comment")
    public void A1Test_validate_specific_comment() {
        User data = new DataHelper().getTestAuthUser();
        given().auth()
                .basic(data.getEmail(),data.getPassword())
                .get( resourcePath +"/v1/comment/" + created_Post.toString()+"/"+created_Comment.toString())
        .then().statusCode(200).spec(responseSpecs.defaultSpec());
    }

    @Test(groups="create_comment")
    public void Test_try_to_show_nonexistent_comment() {
        User data = new DataHelper().getTestAuthUser();
        Response response3 = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .get( resourcePath +"/v1/comment/" + created_Post.toString()+"/"+created_Comment.toString()+"999");
        assertThat(response3.path("error"),equalTo("sql: no rows in result set"));
    }

    @Test(groups="create_comment")
    public void A2Test_Update_comment() {
        Comment testComment = new Comment(DataHelper.generate_title(),DataHelper.generate_content());
        User data = new DataHelper().getTestAuthUser();
        Response response3 = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .body(testComment)
                .put( resourcePath +"/v1/comment/" + created_Post.toString()+"/"+created_Comment.toString());
        int statusCode = response3.getStatusCode();
        assertThat(response3.path("message"),equalTo("Comment updated"));
        assertThat(statusCode,equalTo(200));

    }
    @Test(groups="create_comment")
    public void Test_try_to_update_nonexistent_comment() {
        Comment testComment = new Comment(DataHelper.generate_title(),DataHelper.generate_content());
        User data = new DataHelper().getTestAuthUser();
        Response response3 = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .body(testComment)
                .put( resourcePath +"/v1/comment/" + created_Post.toString()+"/"+created_Comment.toString()+"999");
        assertThat(response3.path("error"),equalTo("Comment not found"));
    }
    @Test(groups="create_comment")
    public void A3Test_delete_comment() {
        User data = new DataHelper().getTestAuthUser();
        Response response3 = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .delete( resourcePath +"/v1/comment/" + created_Post.toString()+"/"+created_Comment.toString());
        int statusCode = response3.getStatusCode();
        assertThat(response3.path("message"),equalTo("Comment deleted"));
        assertThat(statusCode,equalTo(200));

    }
    @Test(groups="create_comment")
    public void Test_try_to_delete_nonexistent_comment() {
        User data = new DataHelper().getTestAuthUser();
        Response response3 = given().auth()
                .basic(data.getEmail(),data.getPassword())
                .delete( resourcePath +"/v1/comment/" + created_Post.toString()+"/"+created_Comment.toString()+"999");
        int statusCode = response3.getStatusCode();
        assertThat(response3.path("message"),equalTo("Comment could not be deleted"));
        assertThat(statusCode,equalTo(406));

    }

}