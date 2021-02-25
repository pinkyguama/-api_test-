import helpers.DataHelper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Article;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;
import specifications.requestSpecs;
import specifications.responseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class articleTest extends baseTest {
    private static String resourcePath = "";
    private static Integer created_Article=0;
    @BeforeGroups("create_article")
    public void createArticle(){
        Article testArticle = new Article(DataHelper.generate_title(),DataHelper.generate_content());
        Response response = given()
                .spec(requestSpecs.generateToken())
                .body(testArticle)
                .post(resourcePath + "/v1/article");
        JsonPath jsonPathEvaluator = response.jsonPath();
        created_Article = jsonPathEvaluator.get("id");

    }
    @Test
    public void Test_CreateArticle_success(){
        Article testArticle = new Article(DataHelper.generate_title(),DataHelper.generate_content());
        given()
        .spec(requestSpecs.generateToken())
               .body(testArticle)
               .post(resourcePath + "/v1/article")
               .then().statusCode(200).spec(responseSpecs.defaultSpec());

    }
    @Test(groups = "create_article")
    public void Test_DeleteArticle_success(){
        given()
                .spec(requestSpecs.generateToken())
                .delete(resourcePath + "/v1/article"+"/" + created_Article.toString())
                .then().statusCode(200).spec(responseSpecs.defaultSpec());

    }
    @Test(groups = "create_article")
            public void Test_Articles_Schema(){
       Response response = given()
                .spec(requestSpecs.generateToken())
                .get(resourcePath + "/v1/articles");
        assertThat(response.asString(), matchesJsonSchemaInClasspath("articles.schema.json"));
        assertThat(response.path("results[0].data[0].id"),equalTo(889));

    }
}
