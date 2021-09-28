package src.test.java.com.epam.tc.hw10.tests;

import assertions.BoardAssertions;
import beans.BoardType;
import core.BoardService;
import java.util.HashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import utils.PropertyReader;

public class BaseTest extends BoardAssertions {

    public Map<String, String> parameters = new HashMap<>();
    public final BoardService boardService = new BoardService();
    public BoardType testBoard;

    @BeforeClass
    public void setUp() {
        parameters.put("name", PropertyReader.getProperty("boardName"));
    }
}
