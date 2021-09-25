package assertions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertEqualsDeep;
import static org.testng.Assert.assertNull;

import beans.BoardType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

public class BoardAssertions {

    public void checkBoardNameAfterCreation(BoardType testBoard, String expectedBoardName) {
        assertEquals(testBoard.getName(), expectedBoardName);
    }

    public void checkBoardNameInResponse(BoardType responseBoard, BoardType expectedBoard) {
        assertEquals(responseBoard, expectedBoard);
    }
    public void checkBoardNameAfterRename(BoardType responseBoard, String newBoardName) {
        assertEquals(responseBoard.getName(), newBoardName);
    }
}
