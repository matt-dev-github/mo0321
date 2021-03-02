import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Rental Agreement Object
 */
class RentalAgreementTest {

    /**
     * This tests creates a rental agreement object with essentially blank information to verify
     * that it's printing function works properly.
     */
    @Test
    void printRentalAgreement() {
        LocalDate testDate = LocalDate.of(2021, Month.FEBRUARY, 28);
        RentalAgreement testAgreement = new RentalAgreement(
                "LADW",
                "Ladder",
                "WBrand",
                1,
                testDate,
                testDate,
                1.99,
                0,
                0.00,
                0,
                0.00,
                0.00);

        String expected =
                """
                        Tool code: LADW
                        Tool type: Ladder
                        Tool brand: WBrand
                        Rental days: 1
                        Check out date: 02/28/2021
                        Due date: 02/28/2021
                        Daily rental charge: $1.99
                        Charge days: 0
                        Pre-discount charge: $0.00
                        Discount percent: 0%
                        Discount amount: $0.00
                        Final charge: $0.00""";

        assertEquals(expected, testAgreement.printRentalAgreement());
    }
}