package src.test.java.com.epam.tc.hw10.tests;

import static core.BoardService.badResponseSpecification;
import static core.BoardService.getBoardFromResponse;
import static core.BoardService.goodResponseSpecification;
import static core.BoardService.requestBuilder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import beans.BoardType;
import io.restassured.http.Method;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utils.TestData;

public class TrelloApiTests {

    private BoardType testBoard;

    @Test(description = "Creating a new board with specific name")
    public void createBoardTest() {

        testBoard = getBoardFromResponse(requestBuilder()
                .setMethod(Method.POST)
                .setName(TestData.getProperty("boardName"))
                .buildRequest()
                .sendRequest()
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());

        assertThat("Board was created with different name", testBoard.getName().equals(TestData.getProperty("boardName")));
        assertThat("Tag id does not exist or is empty", testBoard, Matchers.hasProperty("id", notNullValue()));

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

        assertThat("Response board is not the same as the created board", responseBoard.equals(testBoard));

    }

    @Test(dependsOnMethods = {"createBoardTest"}, description = "Changing the name of the created board")
    public void updateBoardTest() {

        BoardType responseBoard = getBoardFromResponse(requestBuilder()
                .setMethod(Method.PUT)
                .setName(TestData.getProperty("newBoardName"))
                .buildRequest()
                .sendRequest(testBoard.getId())
                .then().assertThat()
                .spec(goodResponseSpecification())
                .extract().response());

        assertThat("tag name from response differs from passed name", responseBoard, Matchers.hasProperty("name", equalTo(TestData.getProperty("newBoardName"))));

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

