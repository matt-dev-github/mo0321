import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 * Utility Class for prompt clerks and executing checkouts
 */
public final class CheckoutFunctions {

    // Scanner used for  clerk input
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * This function executes a checkout and create rental agreements.
     *
     * @param toolToCheckOut Which tool will be checkout
     * @param checkoutDate Which date the checkout was executed on
     * @param rentalDays How many days the tool will be rented for
     * @param discountPercentage What percent of the rental costs will be deducted
     * @return Generated rental agreement
     */
    public static RentalAgreement checkout(Tool toolToCheckOut, LocalDate checkoutDate, int rentalDays, int discountPercentage) {

        // Create and calculate needed values to create a rental agreement
        LocalDate dueDate = calcDueDate(checkoutDate, rentalDays);
        int chargeDays = calcChargeDays(checkoutDate, dueDate, toolToCheckOut, rentalDays);
        double preDiscountCharge = calcPreDiscountCharge(chargeDays, toolToCheckOut.getDailyCharge());
        double discountAmount = calDiscountAmount(preDiscountCharge, discountPercentage);
        double finalCharge = preDiscountCharge - discountAmount;

        return new RentalAgreement(
                toolToCheckOut.getCode(),
                toolToCheckOut.getType(),
                toolToCheckOut.getBrand(),
                rentalDays,
                checkoutDate,
                dueDate,
                toolToCheckOut.getDailyCharge(),
                chargeDays,
                preDiscountCharge,
                discountPercentage,
                discountAmount,
                finalCharge);
    }

    /**
     * This function calculates the amount to deduct from rental costs.
     *
     * @param preDiscountCharge Rental costs before deduction
     * @param discountPercentage Percentage to remove from cost
     * @return Resulting costs after discount
     */
    private static double calDiscountAmount(double preDiscountCharge, double discountPercentage) {
        DecimalFormat df = new DecimalFormat("0.00");
        double deduction = ((100 - discountPercentage) * preDiscountCharge) / 100;
        return  Double.parseDouble(df.format(preDiscountCharge - deduction));
    }

    /**
     * This function returns the date a tool rental will be due back on.
     *
     * @param checkoutDate Date of checkout
     * @param rentalDays How many days the tool will be rented for
     * @return Date that the tool is due back on
     */
    private static LocalDate calcDueDate(LocalDate checkoutDate, int rentalDays) {
        return checkoutDate.plusDays(rentalDays);
    }

    /**
     * This function takes a tool and find how many days the customer will be charged for the rental.
     *
     * @param checkoutDate Date of the checkout
     * @param dueDate Date the rental is due
     * @param toolToRent The tool being rented
     * @param rentalDays Number of days the rental will be for
     * @return The number of days to charge the customer for the rental.
     */
    private static int calcChargeDays(LocalDate checkoutDate, LocalDate dueDate, Tool toolToRent, int rentalDays) {
        // Increment for counting in while loop
        dueDate = dueDate.plusDays(1);
        LocalDate iterDate = checkoutDate.plusDays(1);

        // Create LocalDate Instances to represent holidays.
        LocalDate laborDay = LocalDate
                .of(checkoutDate.getYear(), Month.SEPTEMBER, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

        LocalDate independenceDay = LocalDate
                .of(checkoutDate.getYear(), Month.JULY, 4);
        if (independenceDay.getDayOfWeek() == DayOfWeek.SATURDAY) {
            independenceDay = independenceDay.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        } else if (independenceDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
            independenceDay = independenceDay.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        // Iterate through each day between checkout date and due date.
        // Find number of days to charge for depending on the tool's attributes.
        // Start from rental day count and deduct a day for each day the tools if free to rent.
        while (!iterDate.isEqual(dueDate)) {
            DayOfWeek currentDayOfWeek = iterDate.getDayOfWeek();

            if (!toolToRent.isHolidayCharge() && (iterDate.isEqual(laborDay) || iterDate.isEqual(independenceDay))) {
                    rentalDays--;
            } else if (!toolToRent.isWeekendCharge()) {
                switch (currentDayOfWeek) {
                    case SATURDAY, SUNDAY -> rentalDays--;
                }
            } else if (!toolToRent.isWeekdayCharge()) {
                switch (currentDayOfWeek) {
                    case MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY -> rentalDays--;
                }
            }

            iterDate = iterDate.plusDays(1);
        }

        return rentalDays;
    }

    /**
     * This function calculates the rental charge before discount deductions.
     *
     * @param chargeDays Number of days to charge for the rental
     * @param dailyCharge The tools daily cost for rental
     * @return the rental charge before discount deductions
     */
    private static double calcPreDiscountCharge(int chargeDays, double dailyCharge) {
        return chargeDays * dailyCharge;
    }

    /**
     * This function prompts the clerk, asking how many days the customer would like to rent a tool.
     * The function takes input and makes sure it is a valid number of days for rental.
     *
     * @param rentalToolType What type the tool is
     * @return The number of days that the customer wants to rent the tool for
     */
    public static int askForRentalDayCount(String rentalToolType) {
        System.out.println("How many days would the customer like to rent the "+ rentalToolType +"? (No more than 28)");

        boolean validInputReceived = false;
        int rentalDays = -1;
        String invalidInputResponse = "Please be sure you entered a whole number between 1 and 28";

        // Validates clerk input, asks again if input is invalid
        while(!validInputReceived) {
            try {
                while (!scanner.hasNextInt()) {
                    System.out.println(invalidInputResponse);
                    scanner.next();
                }
                rentalDays = scanner.nextInt();
                if (rentalDays > 0 && rentalDays < 28) {
                    validInputReceived = true;
                } else {
                    throw new InvalidRentalDaysException();
                }
            } catch (InvalidRentalDaysException e) {
                System.out.println(invalidInputResponse);
            }
        }

        return rentalDays;
    }

    /**
     * This function asks the clerk for a percentage to discount from the tool rental.
     * The function takes input and makes sure it is a valid percentage.
     *
     * @return The discount percentage that the clerk applied
     */
    public static int askForCheckoutDiscount() {
        System.out.println("Please enter percentage to discount from this rental. (format: XX)");

        boolean validInputReceived = false;
        int discount = -1;
        String invalidInputResponse = "Please be sure you entered a whole number between 0 and 100.";

        // Validates clerk input, asks again if input is invalid
        while(!validInputReceived) {
            while (!scanner.hasNextInt()) {
                System.out.println(invalidInputResponse);
                scanner.next();
            }
            discount = scanner.nextInt();

            try {
                if (isDiscountValid(discount)) {
                    validInputReceived = true;
                }
            } catch (InvalidDiscountPercentException e) {
                System.out.println(invalidInputResponse);
            }
        }

        return discount;
    }

    /**
     * Helper function to throw an exception if an invalid rental day count is entered.
     *
     * @param rentalDays The number of days to validate
     * @return If the number of rental days is valid
     * @throws InvalidRentalDaysException The exception is thrown if rental day count is invalid
     */
    private static boolean _areRentalDaysValid(int rentalDays) throws InvalidRentalDaysException{
        if (rentalDays >= 1 && rentalDays <= 28) {
            return true;
        } else {
            throw new InvalidRentalDaysException();
        }
    }

    /**
     * Accessible function for the test class
     *
     * @param rentalDays The discount amount to validate
     * @return If the discount percentage is valid
     * @throws InvalidRentalDaysException The exception that is thrown if discount input is invalid
     */
    public static boolean areRentalDaysValid(int rentalDays) throws InvalidRentalDaysException {
        return _areRentalDaysValid(rentalDays);
    }

    /**
     * Helper function to throw an exception if an invalid discount percentage is entered.
     *
     * @param discount The discount amount to validate
     * @return If the discount percentage is valid
     * @throws InvalidDiscountPercentException The exception that is thrown if discount input is invalid
     */
    private static boolean _isDiscountValid(int discount) throws InvalidDiscountPercentException{
        if (discount >= 0 && discount <= 100) {
            return true;
        } else {
            throw new InvalidDiscountPercentException();
        }
    }

    /**
     * Accessible function for the test class
     *
     * @param discount The discount amount to validate
     * @return If the discount percentage is valid
     * @throws InvalidDiscountPercentException the exception that is thrown if discount input is invalid
     */
    public static boolean isDiscountValid(int discount) throws InvalidDiscountPercentException {
        return _isDiscountValid(discount);
    }

    /**
     * This function prompts the clerk, asking which tool the customer would like to rent.
     * The function takes input and makes sure it is a valid tool in the directory.
     *
     * @param toolsAvailable Product codes to pick from
     * @return The selected product code
     */
    public static String askForToolSelection(String[] toolsAvailable) {
        System.out.println("Which tool will the customer be renting?");

        for (int itemIndex = 0; itemIndex < toolsAvailable.length; itemIndex++) {
            System.out.println(itemIndex + ": " + toolsAvailable[itemIndex]);
        }

        System.out.println("Select an Item Index from the list of available tools.");

        String invalidInputResponse = "Please type one of the number options listed.";
        boolean validInputReceived = false;
        int selectedTool = -1;

        // Validates clerk input, asks again if input is invalid
        while (!validInputReceived) {
            while (!scanner.hasNextInt()) {
                System.out.println(invalidInputResponse);
                scanner.next();
            }
            selectedTool = scanner.nextInt();
            if (selectedTool >= 0 && selectedTool < toolsAvailable.length) {
                validInputReceived = true;
            } else {
                System.out.println(invalidInputResponse);
            }
        }

        return toolsAvailable[selectedTool];
    }

    /**
     * This function asks if the customer would like a printout of their rental agreement.
     * The function takes input and makes sure it is valid.
     *
     * @return If the customer wants a printout
     */
    public static boolean offerToPrintRentalAgreement() {
        System.out.println(
                """
                        Would the customer like a printout of their rental agreement?
                        0: Yes
                        1: No
                        """);

        return inputIsYesOrNo();
    }

    /**
     * This function validates that clerked entered responses to yes or no prompts is valid.
     *
     * @return If the input is valid.
     */
    private static boolean inputIsYesOrNo() {
        boolean validInputReceived = false;
        int customerWantsReceipt = -1;

        while(!validInputReceived) {
            while (!scanner.hasNextInt()) {
                System.out.println("Please be sure enter 0 for Yes or 1 for No.");
                scanner.next();
            }
            customerWantsReceipt = scanner.nextInt();
            if (customerWantsReceipt == 0 || customerWantsReceipt == 1) {
                validInputReceived = true;
            }
        }

        return customerWantsReceipt == 0;
    }

    /**
     * This function asks if the clerk will be renting out more tools
     * The function takes input and makes sure it is valid.
     *
     * @return If the clerk will be checking out another rental
     */
    public static boolean offerToContinue() {
        System.out.println(
                """
                        Would you like to checkout another customer?
                        0: Yes
                        1: No
                        """);

        return inputIsYesOrNo();
    }
}
