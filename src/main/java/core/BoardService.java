package core;

import static org.hamcrest.Matchers.lessThan;

import beans.BoardType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpStatus;
import utils.TestData;

public class BoardService {

    public static final URI TRELLO_BOARD_URI = URI.create("https://api.trello.com/1/board/");

    private Method requestMethod;
    private Map<String, String> parameters;

    private BoardService(Map<String, String> parameters, Method requestMethod) {
        this.parameters = parameters;
        this.requestMethod = requestMethod;
    }

    public static BoardApiRequestBuilder requestBuilder() {
        return new BoardApiRequestBuilder();
    }

    public static class BoardApiRequestBuilder {
        private Map<String, String> parameters = new HashMap<>();
        private Method requestMethod = Method.GET;

        public BoardApiRequestBuilder setMethod(Method method) {
            requestMethod = method;
            return this;
        }

        public BoardApiRequestBuilder setName(String name) {
            parameters.put("name", name);
            return this;
        }


        public BoardService buildRequest() {
            return new BoardService(parameters, requestMethod);
        }

    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri(TRELLO_BOARD_URI)
                .build();
    }

    public Response sendRequest(String addPath) {
        return RestAssured
                .given(requestSpecification()).log().all()
                .queryParam("key", TestData.getProperty("key"))
                .queryParam("token", TestData.getProperty("token"))
                .queryParams(parameters)
                .request(requestMethod, TRELLO_BOARD_URI + addPath)
                .prettyPeek();
    }

    public Response sendRequest() {
        return RestAssured
                .given(requestSpecification()).log().all()
                .queryParam("key", TestData.getProperty("key"))
                .queryParam("token", TestData.getProperty("token"))
                .queryParams(parameters)
                .request(requestMethod, TRELLO_BOARD_URI)
                .prettyPeek();
    }

    public static ResponseSpecification goodResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification badResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.TEXT)
                .expectResponseTime(lessThan(10000L))
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
                .build();
    }

    public static BoardType getBoardFromResponse (Response response) {
        BoardType board = new Gson()
                .fromJson(response.asString().trim(), new TypeToken<BoardType>(){}.getType());
        return board;
    }

}
