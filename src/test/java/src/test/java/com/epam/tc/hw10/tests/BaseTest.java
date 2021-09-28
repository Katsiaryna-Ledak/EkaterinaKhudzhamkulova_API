package src.test.java.com.epam.tc.hw10.tests;

import assertions.BoardAssertions;
import beans.BoardType;
import core.BoardService;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import utils.RandomStringGenerator;

public class BaseTest extends BoardAssertions {

    public BoardType testBoard;
    public String randomBoardName;
    public Map<String, String> parameters = new HashMap<>();
    public final BoardService boardService = new BoardService();

    @BeforeClass
    public void setUp() {
        randomBoardName = RandomStringGenerator.generateRandomString();
        parameters.put("name", randomBoardName);
        testBoard = boardService.createBoard(parameters);
    }
}
