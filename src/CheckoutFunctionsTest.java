import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for verifying the functionality of the Checkout utility functions.
 */
class CheckoutFunctionsTest {
    private static ToolsDB db;

    @BeforeAll
    static void init() {
        String toolInfoCSVPath = "src/testToolInfo.csv";
        String toolsAvailableCSVPath = "src/testToolsAvailable.csv";
        db = new ToolsDB(toolInfoCSVPath, toolsAvailableCSVPath);
    }

    @Test
    void testCardinalCase1() {
        int invalidDiscountPercentage = 101;

        assertThrows(InvalidDiscountPercentException.class, () -> {
            CheckoutFunctions.isDiscountValid(invalidDiscountPercentage);
        });
    }

    @Test
    void testCardinalCase2() {
        String[] toolsAvailableToRent = db.getToolsAvailable();
        Tool toolToRent = db.getToolFromCode("LADW");
        LocalDate checkoutDate = LocalDate
                .of(2020, Month.JULY, 2);
        LocalDate dueDate = LocalDate
                .of(2020, Month.JULY, 5);
        int rentalDays = 3;
        int discountPercentage = 10;

        RentalAgreement actual = CheckoutFunctions.checkout(toolToRent, checkoutDate, rentalDays, discountPercentage);
        RentalAgreement expected = new RentalAgreement(toolToRent.getCode(), toolToRent.getType(),
                toolToRent.getBrand(), rentalDays, checkoutDate, dueDate,
                toolToRent.getDailyCharge(), 2,
                3.98, discountPercentage,
                0.40, 3.58);

        assertEquals(expected.printRentalAgreement(), actual.printRentalAgreement());
    }

    @Test
    void testCardinalCase3() {
        String[] toolsAvailableToRent = db.getToolsAvailable();
        Tool toolToRent = db.getToolFromCode("CHNS");
        LocalDate checkoutDate = LocalDate
                .of(2015, Month.JULY, 2);
        LocalDate dueDate = LocalDate
                .of(2015, Month.JULY, 7);
        int rentalDays = 5;
        int discountPercentage = 25;

        RentalAgreement actual = CheckoutFunctions.checkout(toolToRent, checkoutDate, rentalDays, discountPercentage);
        RentalAgreement expected = new RentalAgreement(toolToRent.getCode(), toolToRent.getType(),
                toolToRent.getBrand(), rentalDays, checkoutDate, dueDate,
                toolToRent.getDailyCharge(), 3,
                4.47, discountPercentage,
                1.12, 3.35);

        assertEquals(expected.printRentalAgreement(), actual.printRentalAgreement());
    }

    @Test
    void testCardinalCase4() {
        String[] toolsAvailableToRent = db.getToolsAvailable();
        Tool toolToRent = db.getToolFromCode("JAKD");
        LocalDate checkoutDate = LocalDate
                .of(2015, Month.SEPTEMBER, 3);
        LocalDate dueDate = LocalDate
                .of(2015, Month.SEPTEMBER, 9);
        int rentalDays = 6;
        int discountPercentage = 0;

        RentalAgreement actual = CheckoutFunctions.checkout(toolToRent, checkoutDate, rentalDays, discountPercentage);
        RentalAgreement expected = new RentalAgreement(toolToRent.getCode(), toolToRent.getType(),
                toolToRent.getBrand(), rentalDays, checkoutDate, dueDate,
                toolToRent.getDailyCharge(), 3,
                8.97, discountPercentage,
                0.00, 8.97);

        assertEquals(expected.printRentalAgreement(), actual.printRentalAgreement());
    }

    @Test
    void testCardinalCase5() {
        String[] toolsAvailableToRent = db.getToolsAvailable();
        Tool toolToRent = db.getToolFromCode("JAKR");
        LocalDate checkoutDate = LocalDate
                .of(2015, Month.JULY, 2);
        LocalDate dueDate = LocalDate
                .of(2015, Month.JULY, 11);
        int rentalDays = 9;
        int discountPercentage = 0;

        RentalAgreement actual = CheckoutFunctions.checkout(toolToRent, checkoutDate, rentalDays, discountPercentage);
        RentalAgreement expected = new RentalAgreement(toolToRent.getCode(), toolToRent.getType(),
                toolToRent.getBrand(), rentalDays, checkoutDate, dueDate,
                toolToRent.getDailyCharge(), 5,
                14.95, discountPercentage,
                0.00, 14.95);

        assertEquals(expected.printRentalAgreement(), actual.printRentalAgreement());
    }

    @Test
    void testCardinalCase6() {
        String[] toolsAvailableToRent = db.getToolsAvailable();
        Tool toolToRent = db.getToolFromCode("JAKR");
        LocalDate checkoutDate = LocalDate
                .of(2020, Month.JULY, 2);
        LocalDate dueDate = LocalDate
                .of(2020, Month.JULY, 6);
        int rentalDays = 4;
        int discountPercentage = 50;

        RentalAgreement actual = CheckoutFunctions.checkout(toolToRent, checkoutDate, rentalDays, discountPercentage);
        RentalAgreement expected = new RentalAgreement(toolToRent.getCode(), toolToRent.getType(),
                toolToRent.getBrand(), rentalDays, checkoutDate, dueDate,
                toolToRent.getDailyCharge(), 1,
                2.99, discountPercentage,
                1.50, 1.49);

        assertEquals(expected.printRentalAgreement(), actual.printRentalAgreement());
    }

    @Test
    void checkout() {
        String[] toolsAvailableToRent = db.getToolsAvailable();

        Tool toolToRent = db.getToolFromCode("LADW");

        LocalDate checkoutDate = LocalDate
                .of(LocalDate.now().getYear(), Month.MARCH, 1);
        LocalDate dueDate = LocalDate
                .of(LocalDate.now().getYear(), Month.MARCH, 2);

        int rentalDays = 1;
        int discountPercentage = 0;
        RentalAgreement actual = CheckoutFunctions.checkout(toolToRent, checkoutDate, rentalDays, discountPercentage);

        RentalAgreement expected = new RentalAgreement(toolToRent.getCode(), toolToRent.getType(),
                toolToRent.getBrand(), 1, checkoutDate, dueDate,
                toolToRent.getDailyCharge(), 1,
                1.99, 0,
                0.00, 1.99);

        assertEquals(expected.printRentalAgreement(), actual.printRentalAgreement());
    }

    @Test
    void askForRentalDayCount() throws  InvalidRentalDaysException {
        assertThrows(InvalidRentalDaysException.class, () -> {
            CheckoutFunctions.areRentalDaysValid(100);
        });
        assertTrue(CheckoutFunctions.areRentalDaysValid(10));
    }

    @Test
    void askForCheckoutDiscount() throws InvalidDiscountPercentException {
        assertThrows(InvalidDiscountPercentException.class, () -> {
            CheckoutFunctions.isDiscountValid(101);
        });
        assertTrue(CheckoutFunctions.isDiscountValid(100));
    }
}