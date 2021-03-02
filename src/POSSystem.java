import java.time.LocalDate;

/**
 * POSSystem class that has a main function to serve as the application.
 */
public class POSSystem {

    /**
     * Runs functions for the POSSystem application
     */
    public static void main (String[] args)
    {
        // List file locations for CSVs hold tool information
        String toolInfoCSVPath = "src/ToolInfo.csv";
        String toolsAvailableCSVPath = "src/ToolsAvailable.csv";

        // Use information specified in CSVs to create a directory/database of tools available for rent
        ToolsDB toolDirectory = new ToolsDB(toolInfoCSVPath, toolsAvailableCSVPath);

        // Flag used to continue making rentals or end the program
        boolean stillMakingRentals = true;

        // Loop that runs until the clerk is done checking out tool rentals
        while(stillMakingRentals) {

            // Pull available tools from directory and prompt clerk to select the customer's tool to rent
            String[] toolsAvailableToRent = toolDirectory.getToolsAvailable();
            String selectedToolCode = CheckoutFunctions.askForToolSelection(toolsAvailableToRent);
            Tool toolToRent = toolDirectory.getToolFromCode(selectedToolCode);

            // Ask clerk how long the tool will be rented for
            int rentalDays = CheckoutFunctions.askForRentalDayCount(toolToRent.getType());

            // Ask clerk for discount amount to apply to the the rental cost
            int discountPercentage = CheckoutFunctions.askForCheckoutDiscount();

            // Generate a rental agreement from input gathered
            RentalAgreement ra = CheckoutFunctions.checkout(toolToRent, LocalDate.now(), rentalDays, discountPercentage);

            // Ask the clerk if they will continue to checkout rentals
            boolean userWantsReceipt = CheckoutFunctions.offerToPrintRentalAgreement();
            if (userWantsReceipt) {
                System.out.println(ra.printRentalAgreement());
            };
            stillMakingRentals = CheckoutFunctions.offerToContinue();
        }
    }
}
