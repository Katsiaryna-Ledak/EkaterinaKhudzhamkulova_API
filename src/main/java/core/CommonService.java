package core;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;
import utils.PropertyReader;

public class CommonService {

    protected Method requestMethod;
    protected Map<String, String> parameters;

    protected static String TRELLO_URI = "https://api.trello.com/1";
    protected static String BOARDS_URI = "/boards/";

    public CommonService() {}

    public CommonService(Method requestMethod, Map<String, String> queryParams) {
        this.requestMethod = requestMethod;
        this.parameters = queryParams;
    }

    public RequestSpecification requestSpecification() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        return new RequestSpecBuilder()
            .setBaseUri(TRELLO_URI)
            .setContentType(ContentType.JSON)
            .addQueryParam("key", PropertyReader.getProperty("key"))
            .addQueryParam("token", PropertyReader.getProperty("token"))
            .build();
    }

    public Response requestWithNoParams(Method method, String path) {
        return
            given().spec(requestSpecification()).when().request(method, path)
                   .then().statusCode(200).extract().response();
    }

    public Response requestWithParams(Method method, String path, Map<String, String> parameters) {
        return
            given().spec(requestSpecification()).queryParams(parameters).when().request(method, path)
                   .then().statusCode(200).extract().response();
    }
}
