package src.test.java.com.epam.tc.hw10.tests;

import assertions.BoardAssertions;
import beans.BoardType;
import org.testng.annotations.BeforeClass;

public class BaseTest extends BoardAssertions {

    public BoardType testBoard;

    @BeforeClass
    public void setUp() {
        testBoard = new BoardType();
    }

}
