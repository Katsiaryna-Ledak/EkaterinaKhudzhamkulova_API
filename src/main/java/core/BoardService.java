package core;

import beans.BoardType;
import com.google.gson.GsonBuilder;
import io.restassured.http.Method;
import io.restassured.response.Response;
import java.util.Map;

public class BoardService extends CommonService{

    public BoardService() {
        super();
    }
    public BoardService(Method requestMethod, Map<String, String> queryParams) {
        super(requestMethod, queryParams);
    }

    public BoardType parseResponse(Response rs) {
        return new GsonBuilder().excludeFieldsWithModifiers().create()
                                .fromJson(rs.getBody().asString(), BoardType.class);
    }

    public BoardType createBoard(Map<String, String> parameters) {
        return parseResponse(requestWithParams(Method.POST, BOARDS_URI, parameters));
    }

    public BoardType getBoard(String boardID) {
        return parseResponse(requestWithNoParams(Method.GET, BOARDS_URI + boardID));
    }

    public BoardType updateBoard(Map<String, String> parameters, String boardID) {
        return parseResponse(requestWithParams(Method.PUT, BOARDS_URI + boardID, parameters));
    }

    public Response deleteBoard(String boardID) {
        return requestWithNoParams(Method.DELETE, BOARDS_URI + boardID);
    }
}
