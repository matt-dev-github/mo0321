import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the tool object class.
 */
class ToolTest {

    private static Tool testTool; // tool used for testing purposes

    @BeforeAll
    static void init() {
        testTool = new Tool(
                "Ladder",
                "Werner",
                "LADW",
                1.99,
                true,
                true,
                false
        );
    }

    @Test
    void getType() {
        assertEquals("Ladder", testTool.getType());
    }

    @Test
    void getBrand() {
        assertEquals("Werner", testTool.getBrand());
    }

    @Test
    void getCode() {
       assertEquals("LADW", testTool.getCode());
    }

    @Test
    void getDailyCharge() { assertEquals(1.99, testTool.getDailyCharge()); }

    @Test
    void isWeekdayCharge() { assertTrue(testTool.isWeekdayCharge()); }

    @Test
    void isWeekend() { assertTrue(testTool.isWeekendCharge()); }

    @Test
    void isHolidayCharge() { assertFalse(testTool.isHolidayCharge()); }

}