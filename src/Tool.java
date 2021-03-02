/**
 * Class Definition for a Tool Object
 */
public class Tool {

    private String type; // The type of tool this is
    private String brand; // The brand that makes this tool
    private String code; // The code for this tool
    private double dailyCharge; // How much this tool costs
    private boolean weekdayCharge; // If this tool costs money on weekdays
    private boolean weekendCharge; // If this tool costs money on weekends
    private boolean holidayCharge; // If this tool costs money on holidays

    /**
     * Constructor for Tool Objects.
     *
     * @param type The type of tool this is
     * @param brand The brand that makes this tool
     * @param code The code for this tool
     * @param dailyCharge How much this tool costs
     * @param weekdayCharge If this tool costs money on weekdays
     * @param weekendCharge If this tool costs money on weekends
     * @param holidayCharge If this tool costs money on holidays
     */
    public Tool(String type, String brand, String code, double dailyCharge, boolean weekdayCharge,
                boolean weekendCharge, boolean holidayCharge) {
        this.type = type;
        this.brand = brand;
        this.code = code;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    /**
     * Getter function for the tool's type.
     *
     * @return Type of tool that this tool is.
     */
    public String getType() {
        return type;
    }

    /**
     * Getter function for the tool's brand.
     *
     * @return Brand that makes this tool.
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Getter function for the tool's code.
     *
     * @return Product code associated with this tool.
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter function for the tool's daily rental cost.
     *
     * @return How much this tool costs.
     */
    public double getDailyCharge() {
        return dailyCharge;
    }

    /**
     * Getter function for the tool's sale attribute indicating if it costs money on weekdays.
     *
     * @return If this tool costs money on weekdays.
     */
    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    /**
     * Getter function for the tool's sale attribute indicating if it costs money on weekends.
     *
     * @return If this tool costs money on weekends.
     */
    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    /**
     * Getter function for the tool's sale attribute indicating if it costs money on holidays.
     *
     * @return If this tool costs money on holidays.
     */
    public boolean isHolidayCharge() {
        return holidayCharge;
    }
}
