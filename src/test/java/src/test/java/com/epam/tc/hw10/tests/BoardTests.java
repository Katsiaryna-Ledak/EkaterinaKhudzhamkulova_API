package src.test.java.com.epam.tc.hw10.tests;

import static core.BoardService.badResponseSpecification;
import static core.BoardService.getBoardFromResponse;
import static core.BoardService.goodResponseSpecification;
import static core.BoardService.requestBuilder;

import assertions.BoardAssertions;
import beans.BoardType;
import io.restassured.http.Method;
import org.testng.annotations.Test;
import utils.PropertyReader;

public class BoardTests extends BaseTest{

    BoardAssertions assertionsForBoard = new BoardAssertions();

    @Test(description = "Creating a new board with specific name")
    public void createBoardTest() {

        testBoard = getBoardFromResponse(requestBuilder()
                .setMethod(Method.POST)
                .setName(PropertyReader.getProperty("boardName"))
                .buildRequest()
                .sendRequest()
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());

        assertionsForBoard.checkBoardNameAfterCreation(testBoard, PropertyReader.getProperty("boardName"));
    }

    @Test(dependsOnMethods = "createBoardTest", description = "Getting a board that was created in previous test and check that they are the same")
    public void getBoardTest() {

        BoardType responseBoard = getBoardFromResponse(requestBuilder()
                .setMethod(Method.GET)
                .buildRequest()
                .sendRequest(testBoard.getId())
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());

        assertionsForBoard.checkBoardNameInResponse(responseBoard, testBoard);
    }

    @Test(dependsOnMethods = {"createBoardTest"}, description = "Changing the name of the created board")
    public void updateBoardTest() {

        BoardType responseBoard = getBoardFromResponse(requestBuilder()
                .setMethod(Method.PUT)
                .setName(PropertyReader.getProperty("newBoardName"))
                .buildRequest()
                .sendRequest(testBoard.getId())
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());

        assertionsForBoard.checkBoardNameAfterRename(responseBoard, PropertyReader.getProperty("newBoardName"));
    }

    @Test(dependsOnMethods = {"createBoardTest", "getBoardTest"}, description = "Deleting created board by ID")
    public void deleteBoardById() {

        requestBuilder()
            .setMethod(Method.DELETE)
            .buildRequest()
            .sendRequest(testBoard.getId())
            .then().assertThat()
            .spec(goodResponseSpecification());

        requestBuilder()
            .setMethod(Method.GET)
            .buildRequest()
            .sendRequest(testBoard.getId())
            .then().assertThat()
            .spec(badResponseSpecification());
    }
}

