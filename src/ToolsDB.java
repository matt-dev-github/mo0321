import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class defines a tool database/directory for the system to create tool objects from.
 */
public class ToolsDB {

    // Map of Each tool available st the store
    private static HashMap<String,Tool> toolsAvailable;

    // Map of sale information for each tool type
    private static HashMap<String, Object[]> toolInformation;

    /**
     * Constructor for the ToolsDB class.
     *
     * @param toolInfoCSVPath Path to the CSV file with sale attributes for each tool type
     * @param toolsAvailableCSVPath Path to the CSV file with tools available for rental
     */
    public ToolsDB(String toolInfoCSVPath, String toolsAvailableCSVPath) {
        toolInformation = new HashMap<String, Object[]>();
        toolsAvailable = new HashMap<String, Tool>();
        updateToolInfo("src/ToolInfo.csv");
        updateToolsAvailable("src/ToolsAvailable.csv");
    }

    /**
     * This function updates the available tools for rental.
     *
     * @param path Path to CSV file with available tools
     */
    private static void updateToolsAvailable(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {

                // break up each line of CSV
                String[] data = line.split(",");
                String toolType = data[0];
                String toolBrand = data[1];
                String toolCode = data[2];

                Object[] salesInfo = toolInformation.get(toolType);
                double dailyCharge = (double) salesInfo[0];
                boolean weekdayCharge = (boolean) salesInfo[1];
                boolean weekendCharge = (boolean) salesInfo[2];
                boolean holidayCharge = (boolean) salesInfo[3];

                Tool toolToAdd = new Tool(
                        toolType,
                        toolBrand,
                        toolCode,
                        dailyCharge,
                        weekdayCharge,
                        weekendCharge,
                        holidayCharge);

                toolsAvailable.put(toolCode, toolToAdd);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function updates the sale attributes for each tool type.
     *
     * @param path Path to CSV file with tool sale attributes
     */
    private static void updateToolInfo(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {

                // break up each line of CSV
                String[] data = line.split(",");

                // create placeholder for tool attributes
                Object[] toolSalesInfo = new Object[4];

                // read available inventory and fill tool info with fields from value of information map
                // convert csv input into variables needed for tool instance and push to map
                String toolType = data[0];
                toolSalesInfo[0] = NumberFormat.getCurrencyInstance(Locale.US).parse(data[1]).doubleValue();
                toolSalesInfo[1] = (data[2].equals("Yes"));
                toolSalesInfo[2] = (data[3].equals("Yes"));
                toolSalesInfo[3] = (data[4].equals("Yes"));
                toolInformation.put(toolType, toolSalesInfo);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function retrieves the desired tool information based on the product code entered.
     *
     * @param toolCode Selected tool code
     * @return A tool object to provide information for checkout
     */
    public Tool getToolFromCode(String toolCode) {
        return toolsAvailable.get(toolCode);
    }

    /**
     * This function retrieves all the product codes of available tools.
     *
     * @return Each product code of the available tools
     */
    public String[] getToolsAvailable() {
        return toolsAvailable.keySet().toArray(new String[0]);
    }
}
