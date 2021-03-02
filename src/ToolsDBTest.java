import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the functionality of the ToolDB class
 */
class ToolsDBTest {

    private static ToolsDB testDB; // ToolDB to use for testing

    // paths to test CSV files to use
    private static final String toolInfoCSVPath = "src/testToolInfo.csv";
    private static final String toolsAvailableCSVPath = "src/testToolsAvailable.csv";

    @BeforeAll
    static void init() {
        testDB = new ToolsDB(toolInfoCSVPath, toolsAvailableCSVPath);
    }

    @Test
    void getToolFromCode() {
        Tool actualLadder = testDB.getToolFromCode("LADW");

        Tool expectedLadder = new Tool(
                "Ladder",
                "Werner",
                "LADW",
                1.99,
                true,
                true,
                false
        );

        assertEquals(expectedLadder.getType(), actualLadder.getType());
        assertEquals(expectedLadder.getBrand(), actualLadder.getBrand());
        assertEquals(expectedLadder.getCode(), actualLadder.getCode());
        assertEquals(expectedLadder.getDailyCharge(), actualLadder.getDailyCharge());
        assertEquals(expectedLadder.isWeekdayCharge(), actualLadder.isWeekdayCharge());
        assertEquals(expectedLadder.isWeekendCharge(), actualLadder.isWeekendCharge());
        assertEquals(expectedLadder.isHolidayCharge(), actualLadder.isHolidayCharge());
    }

    @Test
    void getToolsAvailable() {
        String[] actualTools = testDB.getToolsAvailable();

        ArrayList<String> expectedTools = new ArrayList<String>();
        expectedTools.add("LADW");
        expectedTools.add("CHNS");
        expectedTools.add("JAKR");
        expectedTools.add("JAKD");

        for (String actualTool : actualTools) {
            assertTrue(expectedTools.contains(actualTool));
        }
    }
}