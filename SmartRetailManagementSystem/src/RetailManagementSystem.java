import java.util.ArrayList;
import java.util.Scanner;

/**
 * Demonstrates a console-based smart retail management system.
 * <p>This system allows users to log in as staff or customers.</p>
 * <p>Staff can manage products, customers, transactions, and update their own accounts credential.</p>
 * <p>Customers can browse and purchase items, view their own order records, and manage their own accounts.</p>
 */
public class RetailManagementSystem {

    /**
     * Runs the smart retail management system demonstration.
     * @param args Not used.
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        boolean isContinue = true;

        // Initialise data collections using ArrayList
        ArrayList<Staff> staffList = new ArrayList<>();
        ArrayList<Customer> customerList = new ArrayList<>();
        ArrayList<Item> itemList = new ArrayList<>();
        ArrayList<Payment> transactionHistory = new ArrayList<>();

        //==== Sample: items(products, gift cards, bundle deals) ====
        itemList.add(new Product("P001", "GOLDEN CHURN BUTTER 250G", 14.50, "INGREDIENTS", 0));
        itemList.add(new Product("P002", "GENERAL PURPOSE FLOUR 1KG", 3.20, "INGREDIENTS", 20));
        itemList.add(new Product("P003", "CSR CASTER SUGAR 1KG", 6.30, "INGREDIENTS", 50));
        itemList.add(new Product("P004", "6-INCH ALUMINIUM CAKE PAN", 19.50, "ACCESSORIES", 50));
        itemList.add(new Product("P005", "STAINLESS STEEL TURNTABLE", 33.50, "ACCESSORIES", 60));
        itemList.add(new Product("P006", "PALETTE KNIFE SET", 22.50, "ACCESSORIES", 100));
        itemList.add(new Product("P007", "DECORATING TOOLS SET", 20.90, "ACCESSORIES", 20));

        itemList.add(new GiftCard("G001", "GIFT CARD RM10", 10.00, 10, "20-04-2026"));
        itemList.add(new GiftCard("G002", "GIFT CARD RM20", 20.00, 20, "20-11-2026"));
        itemList.add(new GiftCard("G003", "GIFT CARD RM50", 50.00, 50, "20-11-2026"));

        String bakerStarterPack = "2x GOLDEN CHURN BUTTER 250G, 1x GENERAL PURPOSE FLOUR 1KG, 1x CSR CASTER SUGAR, 1x 6-INCH ALUMINIUM CAKE PAN";
        String cakeDesignPack = "1x STAINLESS STEEL TURNTABLE, 1x PALETTE KNIFE SET, 1x DECORATING TOOLS SET";
        itemList.add(new BundleDeal("B001", "BAKER'S STARTER PACK", 52.20,10, bakerStarterPack));
        itemList.add(new BundleDeal("B002", "CAKE DECORATOR PACK", 69.21, 10, cakeDesignPack));

        //==== Sample: customer ====
        customerList.add(new Customer("Sample Customer", "cust", "cust", "0123456789", "user@gmail.com", itemList, transactionHistory, customerList));

        //==== Sample: staff ====
        staffList.add(new Staff("Staff", "staff", "staff@123", "I260419", "Admin", customerList, itemList, transactionHistory));

        // Prompt user continuously if choice invalid
        while (isContinue) {
            System.out.println("""
                    \n=====================================
                    |      Welcome to Smart Retail      |
                    =====================================
                    Select User Type (enter "X" to exit)
                    \t(S) Staff
                    \t(C) Customer
                    """);
            System.out.print(" Enter code: ");
            String choice = input.nextLine().toUpperCase().trim();

            switch (choice) {
                case "S" -> {
                    int attempt = 0;
                    boolean success = false;
                    Staff targetStaff;

                    while (attempt < 3 && !success) {
                        String[] loginData = showLoginPrompt(input);  // Call login screen
                        String username = loginData[0];
                        String password = loginData[1];

                        targetStaff = null; // Reset every time user enter credential

                        // Search for the specific staff
                        for (Staff staff : staffList) {
                            if (staff.getUsername().equalsIgnoreCase(username)) {
                                targetStaff = staff;
                                break;
                            }
                        }

                        // Check if this staff found matches username and password in the system
                        if (targetStaff != null && targetStaff.authenticate(username, password)) {

                            // Check if this staff account status is Active(true) or Deactivated(false)
                            if (targetStaff.getStatus()) {
                                targetStaff.onLoginSuccess(input);
                                success = true;
                            } else {
                                System.out.println("\n Error: Account is locked. Please contact manager.");
                                break;
                            }
                        } else {
                            attempt++;
                            boolean continueLogin = handleFailedAttempt(targetStaff, attempt, input); // Call 3x login attempt account checked method
                            if (!continueLogin) {
                                break;
                            }
                        }
                    }
                }
                case "C" -> {
                    int attempt = 0;
                    boolean success = false;
                    Customer targetCustomer;

                    while (attempt < 3 && !success) {
                        String[] loginData = showLoginPrompt(input);
                        String username = loginData[0];
                        String password = loginData[1];

                        targetCustomer = null;

                        for (Customer customer : customerList) {
                            if (customer.getUsername().equalsIgnoreCase(username)) {
                                targetCustomer = customer;
                                break;
                            }
                        }
                        if (targetCustomer != null && targetCustomer.authenticate(username, password)) {
                            if (targetCustomer.getStatus()) {
                                targetCustomer.onLoginSuccess(input);
                                success = true;
                            } else {
                                System.out.println("\n Error: Account is locked. Please contact customer service");
                                break;
                            }
                        } else {
                            attempt++;
                            boolean continueLogin = handleFailedAttempt(targetCustomer, attempt, input);
                            if (!continueLogin) {
                                break;
                            }
                        }
                    }
                }
                case "X" -> {
                    System.out.println("\n*****\nThank you for using Smart Retail Management System.\n*****");
                    isContinue = false;
                }
                default -> System.out.println("\n*****\n Error: Invalid input.\n*****\n");
            }
        }
    }

    /**
     * Displays the login interface for users to log in to the system.
     * @param input The Scanner object used to read user username and password input.
     * @return A String array containing:
     * [0] username
     * [1] password
     */
    private static String[] showLoginPrompt(Scanner input) {
        System.out.println("""
                ====================================
                |             L O G I N            |
                ====================================
                """);

        System.out.print("\tUsername: ");
        String username = input.nextLine().trim();

        System.out.print("\tPassword: ");
        String password = input.nextLine().trim();

        return new String[]{username, password};
    }

    /**
     * Handles failed login attempts and lets user decide whether to continue logging in.
     * @param user The user that make the login attempt.
     * @param attempt The number of login attempts made by the user.
     * @param input The Scanner object used to read user Yes(Y) or No(N) input.
     * @return true if user wants to continue logging in. Otherwise, false.
     */
    private static boolean handleFailedAttempt(User user, int attempt, Scanner input) {
        if (user == null) {
            System.out.println("\n*****\n Invalid username or password.\n*****");
            System.out.println("You have " + (3 - attempt) + " attempt(s) left.\n");

            return Utility.promptYesNo(input, "Try logging in again?");

        }
        if (user.getStatus()) {
            if (attempt >= 3) {
                System.out.println("Account has been locked. Please contact support.");
                user.toggleStatus();

                return Utility.promptYesNo(input, "Try logging in again?");

            } else {
                System.out.println("\n*****\n Invalid username or password.\n*****");
                System.out.println("You have " + (3 - attempt) + " attempt(s) left.\n");

                return Utility.promptYesNo(input, "Try logging in again?");
            }
        }
        else {
            System.out.println("""
                    Your account is already locked. Please contact customer service.
                    """);

            return Utility.promptYesNo(input, "Try logging in again?");
        }
    }
}