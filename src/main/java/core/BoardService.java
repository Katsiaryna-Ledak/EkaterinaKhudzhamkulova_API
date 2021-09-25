package core;

import beans.BoardType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import utils.PropertyReader;

public class BoardService extends CommonService{

    public static final URI TRELLO_BOARD_URI = URI.create("https://api.trello.com/1/board/");

    public BoardService(Method requestMethod, Map<String, String> queryParams) {
        super(requestMethod, queryParams);
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
            return new BoardService(requestMethod, parameters);
        }

        public BoardApiRequestBuilder setId(String id) {
            parameters.put("id", id);
            return this;
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
                .queryParam("key", PropertyReader.getProperty("key"))
                .queryParam("token", PropertyReader.getProperty("token"))
                .queryParams(parameters)
                .request(requestMethod, TRELLO_BOARD_URI + addPath)
                .prettyPeek();
    }

    public Response sendRequest() {
        return RestAssured
                .given(requestSpecification()).log().all()
                .queryParam("key", PropertyReader.getProperty("key"))
                .queryParam("token", PropertyReader.getProperty("token"))
                .queryParams(parameters)
                .request(requestMethod, TRELLO_BOARD_URI)
                .prettyPeek();
    }

    public static BoardType getBoardFromResponse (Response response) {
        BoardType board = new Gson()
                .fromJson(response.asString().trim(), new TypeToken<BoardType>(){}.getType());
        return board;
    }

}
