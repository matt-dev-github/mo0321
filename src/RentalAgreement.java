import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Represents a Rental Agreement.
 * @author Matt Otto
 */
public class RentalAgreement {

    private final String toolCode; // Code of tool being rented
    private final String toolType; // Type of tool being rented
    private final String toolBrand; // Brand of tool being rented
    private final double dailyRentalCharge; // Daily cost of tool being rented
    private final int rentalDays; // Number of days the tool will be rented for
    private final LocalDate checkoutDate; // Date the rental item was checked out on
    private final LocalDate dueDate; // Date the rental item will be due on
    private final int chargeDays; // Count of days customer will be billed for
    private final double preDiscountCharge; // Cost of rental without clerk discount applied
    private final int discountPercent; // Percentage to deduct from rental price
    private final double discountAmount; // Cost amount to be deducted from rental cost
    private final double finalCharge; // Cost of rental after discount has been applied

    /**
     * Constructor for a Rental Agreement object.
     *
     * @param toolCode Code of tool being rented
     * @param toolType Type of tool being rented
     * @param toolBrand Brand of tool being rented
     * @param rentalDays Number of days the tool will be rented for
     * @param checkoutDate Date the rental item was checked out on
     * @param dueDate Date the rental item will be due on
     * @param dailyRentalCharge Daily cost of tool being rented
     * @param chargeDays Count of days customer will be billed for
     * @param preDiscountCharge Cost of rental without clerk discount applied
     * @param discountPercent Percentage to deduct from rental price
     * @param discountAmount Cost amount to be deducted from rental cost
     * @param finalCharge Cost of rental after discount has been applied
     */
    public RentalAgreement(String toolCode, String toolType, String toolBrand, int rentalDays, LocalDate checkoutDate,
                           LocalDate dueDate, double dailyRentalCharge, int chargeDays, double preDiscountCharge,
                           int discountPercent, double discountAmount, double finalCharge)
    {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
        this.dailyRentalCharge = dailyRentalCharge;
        this.chargeDays = chargeDays;
        this.preDiscountCharge = preDiscountCharge;
        this.discountPercent = discountPercent;
        this.discountAmount = discountAmount;
        this.finalCharge = finalCharge;
    }

    /**
     * Method to print the Rental Agreement to the console.
     *
     * @return String representing a printout of all of the rental information at checkout.
     */
    public String printRentalAgreement() {

        // Formatter to print currency amounts
        NumberFormat priceFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // String Buiilder because += reconstructs a new string
        StringBuilder retString = new StringBuilder();

        retString.append("Tool code: " + toolCode + "\n")
                .append("Tool type: " + toolType + "\n")
                .append("Tool brand: " + toolBrand + "\n")
                .append("Rental days: " + rentalDays + "\n")
                .append("Check out date: " + checkoutDate.format(dateFormatter) + "\n")
                .append("Due date: " + dueDate.format(dateFormatter) + "\n")
                .append("Daily rental charge: " + priceFormatter.format(dailyRentalCharge) + "\n")
                .append("Charge days: " + chargeDays + "\n")
                .append("Pre-discount charge: " + priceFormatter.format(preDiscountCharge) + "\n")
                .append("Discount percent: " + discountPercent + "%" + "\n")
                .append("Discount amount: " + priceFormatter.format(discountAmount) + "\n")
                .append("Final charge: ").append(priceFormatter.format(finalCharge));

        return retString.toString();
    }
}
