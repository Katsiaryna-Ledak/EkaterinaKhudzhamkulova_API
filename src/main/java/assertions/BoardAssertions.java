package assertions;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import beans.BoardType;
import io.restassured.response.Response;

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
    public void CheckResponseAfterDeletingBoard(Response response) {
        assertNull(response.path("_value"));
    }
}
