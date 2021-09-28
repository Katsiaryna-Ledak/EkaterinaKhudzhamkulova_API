package src.test.java.com.epam.tc.hw10.tests;

import assertions.BoardAssertions;
import beans.BoardType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.PropertyReader;

public class BoardTests extends BaseTest{

    BoardAssertions assertionsForBoard = new BoardAssertions();

    @Test(description = "Creating a new board with specific name")
    public void createBoardTest() {

        testBoard = boardService.createBoard(parameters);
        assertionsForBoard.checkBoardNameAfterCreation(testBoard, PropertyReader.getProperty("boardName"));
    }

    @Test(dependsOnMethods = "createBoardTest", description = "Getting a board that was created in previous test and check that they are the same")
    public void getBoardTest() {

        BoardType responseBoard = boardService.getBoard(testBoard.getId());
        assertionsForBoard.checkBoardNameInResponse(responseBoard, testBoard);
    }

    @Test(dependsOnMethods = {"createBoardTest"}, description = "Changing the name of the created board")
    public void updateBoardTest() {

        parameters.put("name", PropertyReader.getProperty("newBoardName"));
        BoardType responseBoard = boardService.updateBoard(parameters, testBoard.getId());
        assertionsForBoard.checkBoardNameAfterRename(responseBoard, PropertyReader.getProperty("newBoardName"));
    }

    @Test(dependsOnMethods = {"createBoardTest", "getBoardTest"}, description = "Deleting created board by ID")
    public void deleteBoardById() {

        Response rs = boardService.deleteBoard(testBoard.getId());
        assertionsForBoard.CheckResponseAfterDeletingBoard(rs);
    }
}

