import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains reusable helper methods for smart retail system.
 * <p>A utility class that provides methods that can be reused throughout the system.</p>
 */
public class Utility {

    /**
     * Prompts the user to select Yes or No.
     * @param input The Scanner object used to read user Yes(Y) or No(N) input.
     * @param message The prompt message in different use place.
     * @return true if user selects "Y", false if "N".
     */
    public static boolean promptYesNo(Scanner input, String message) {
        System.out.print("\n" + message + " Yes(Y) / No(N): ");
        String select = input.nextLine().toUpperCase().trim();
        System.out.println();
        while (!select.equals("Y") && !select.equals("N")) {
            System.out.print("Invalid choice. Enter Y or N: ");
            select = input.nextLine().toUpperCase().trim();
        }

        return select.equals("Y");
    }

    /**
     * Generates a unique item code for items including product, gift card, and bundle deal.
     * @param itemList The list of existing items.
     * @param prefix The prefix used to differentiate item types.
     * @return A formatted item code that has the prefix and next available number based on existing items (e.g:P002).
     */
    public static String generateItemCode(ArrayList<Item> itemList, String prefix) {
        int max = 0;

        for (Item item : itemList) {
            String code = item.getItemCode();

            if (code.startsWith(prefix)) {
                String numberPart = code.substring(prefix.length());
                int num = Integer.parseInt(numberPart);

                if (num > max) {
                    max = num;
                }
            }
        }

        return String.format("%s%03d", prefix, max + 1);
    }

    /**
     * Prompts user to input basic item details.
     * @param input The Scanner object used to read user input.
     * @param itemList The list of existing items used for item code generation.
     * @param prefix The prefix used for the item code.
     * @return A String array containing:
     * [0] item code
     * [1] name
     * [2] price
     */
    public static String[] inputItemCommonDetails(Scanner input, ArrayList<Item> itemList, String prefix) {

        String code = generateItemCode(itemList, prefix);
        System.out.printf("%-20s: %s\n", " Item Code", code);

        System.out.printf("%-20s: ", " Name");
        String name = input.nextLine().toUpperCase().trim();

        String price;
        boolean isValid = false;

        do {
            System.out.printf("%-20s: ", " Price (RM)");
            price = input.nextLine().trim(); // Store price as String first to avoid input format errors

            try {
                double priceInt = Double.parseDouble(price);

                if (priceInt <= 0) {
                    System.out.println("Error: Price should not less than 0.");
                } else {
                    isValid = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Invalid price input.");
            }
        } while (!isValid);

        return new String[]{code, name, price};
    }

    /**
     * Searches the item list and returns the item that matches the given item code.
     * @param itemList The list of items to search.
     * @param searchCode The item code used to search for the item.
     * @return The matching item if found. Otherwise, null.
     */
    public static Item findItemByCode(ArrayList<Item> itemList, String searchCode) {
        for (Item item : itemList) {
            if (item.getItemCode().equalsIgnoreCase(searchCode)) {
                return item;
            }
        }
        return null;
    }

    /**
     * Checks whether the customer's contact number already exists.
     * @param customerList The list of existing customers.
     * @param newContactNo The new contact number entered by the user.
     * @return true if contact number already exists; otherwise, false.
     */
    public static boolean isContactNumberDuplicate(ArrayList<Customer> customerList, String newContactNo) {
        for (Customer customer : customerList) {
            if (customer.getContactNo().equals(newContactNo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the customer's email already exists.
     * @param customerList The list of existing customers.
     * @param newEmail The new email entered by the user.
     * @return true if email already exists; otherwise, false.
     */
    public static boolean isEmailDuplicate(ArrayList<Customer> customerList, String newEmail) {
        for (Customer customer : customerList) {
            if (customer.getEmail().equals(newEmail)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the customer's Username number already exists.
     * @param customerList The list of existing customers.
     * @param newUsername The new Username entered by the user.
     * @return true if email already exists; otherwise, false.
     */
    public static boolean isUsernameDuplicate(ArrayList<Customer> customerList, String newUsername) {
        for (Customer customer : customerList) {
            if (customer.getUsername().equalsIgnoreCase(newUsername)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the rounding adjustment and rounded total amount after discount is applied.
     * @param subtotal The total amount after discount is applied.
     * @return A double array containing:
     *[0] rounding adjustment,
     *[1] rounded total amount.
     */
    public static double[] roundingAdjustment(double subtotal){
        double roundedTotal = Math.round(subtotal * 20.0) / 20.0;
        double roundingAdj = roundedTotal - subtotal;

        return new double[]{roundingAdj, roundedTotal};
    }
}