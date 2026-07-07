import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a customer who can log in and shop in smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from User class.</p>
 */
public class Customer extends User implements Transactable {
    private String custId;
    private String contactNo;
    private String email;
    private ArrayList<Item> itemList;
    private ArrayList<Item> cart;
    private ArrayList<Payment> transactionHistory;
    private ArrayList<Customer> customerList;

    /**
     * Constructs a Customer object.
     *
     * @param name The customer's name.
     * @param username The customer's login username.
     * @param password The customer's login password.
     * @param contactNo The customer's contact number.
     * @param email The customer's email address.
     * @param itemList The shared item list used for browsing and shopping.
     * @param transactionHistory The shared transaction history list.
     * @param customerList The list of existing customers used for customer ID generation.
     */

    public Customer(String name, String username, String password, String contactNo, String email,
                    ArrayList<Item> itemList, ArrayList<Payment> transactionHistory, ArrayList<Customer> customerList) {
        super(name, username, password);
        generateAndSetCustId(customerList, "C");
        this.contactNo = contactNo;
        this.email = email;
        this.cart = new ArrayList<>();
        this.itemList = itemList;
        this.transactionHistory = transactionHistory;
        this.customerList = customerList;
    }

    /**
     * Returns the customer ID.
     *
     * @return The customer ID.
     */
    public String getCustId() { return this.custId; }

    /**
     * Returns the customer contact number.
     *
     * @return The contact number.
     */
    public String getContactNo() {
        return this.contactNo;
    }

    /**
     * Returns the customer email address.
     *
     * @return The email address.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Generates and sets a unique customer ID based on the existing customer list.
     *
     * @param customerList The list of existing customers.
     * @param prefix The prefix used for the customer ID.
     */
    public void generateAndSetCustId(ArrayList<Customer> customerList, String prefix){
        int max = 0;

        for (Customer customer : customerList) {
            String code = customer.getCustId();

            if (code.startsWith(prefix)) {
                String numberPart = code.substring(prefix.length());
                int num = Integer.parseInt(numberPart);

                if (num > max) {
                    max = num;
                }
            }
        }
        this.custId = String.format("%s%03d", prefix, max + 1);
    }

    /**
     * Updates the customer contact number after validating the format.
     *
     * @param contactNo The new contact number.
     * @return true if the contact number is valid and updated, otherwise false.
     */
    public boolean setContactNo(String contactNo){
        if (!isValidContactNo(contactNo)) {
            return false;
        }
        this.contactNo = contactNo;
        return true;
    }

    /**
     * Updates the customer email after validating the format.
     *
     * @param email The new email address.
     * @return true if the email is valid and updated, otherwise false.
     */
    public boolean setEmail(String email){
        if (!isValidEmail(email)) {
            return false;
        }
        this.email = email;
        return true;
    }

    /**
     * Validates the customer contact number format.
     *
     * @param contactNo The contact number to validate.
     * @return true if the contact number is valid, otherwise false.
     */
    public static boolean isValidContactNo(String contactNo) {
        if (contactNo == null || contactNo.trim().isEmpty()) {
            System.out.println("Contact cannot be empty.");
            return false;
        }

        contactNo = contactNo.trim();

        if (!contactNo.matches("^01\\d{8,9}$")) {
            System.out.println("Invalid contact number.");
            return false;
        }
        return true;
    }

    /**
     * Validates the customer email format.
     *
     * @param email The email address to validate.
     * @return true if the email is valid, otherwise false.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Email cannot be empty.");
            return false;
        }

        email = email.trim();

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            System.out.println("Email must contain '@' and domain.");
            return false;
        }
        return true;
    }

    /**
     * Handles actions after a successful customer login.
     *
     * @param input The Scanner object used to read customer input.
     */
    @Override
    public void onLoginSuccess(Scanner input) {
        this.input = input;
        System.out.println("\n Customer login successful.\n");
        customerDashboard();
    }

    /**
     * Displays the customer dashboard and handles customer menu navigation.
     */
    private void customerDashboard() {
        String choice;
        boolean isContinue = true;

        while (isContinue) {
            System.out.println("\n====================================");
            System.out.println("\tDear, " + getName());
            System.out.println("====================================");
            System.out.println("""
                    \tWhat do you want to do?
                    \t1. Browse Items
                    \t2. View Order Records
                    \t3. Manage Account
                    \t4. Logout
                    """);
            System.out.print("  Enter choice: ");
            choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> browseItems();
                case "2" -> viewOrderRecords();
                case "3" -> manageAccount();
                case "4" -> {
                    if (Utility.promptYesNo(input, "Confirm to logout?")) {
                        isContinue = false;
                    }
                }
                default -> System.out.println("\n*****\n Error: Invalid input.\n*****\n");
            }
        }
    }

    /**
     * Displays the browse items menu and allows customer to view products,
     * gift cards, bundle deals, cart, and checkout.
     */
    private void browseItems() {
        if (itemList == null || itemList.isEmpty()) {
            System.out.println("\n No items available.\n");
            return;
        }

        boolean backToShopping  = false;

        while (!backToShopping) {
            System.out.println("""
                    \n====================================
                    \tBrowse Items
                    ====================================
                    \t1. Product
                    \t2. Gift Card
                    \t3. Bundle Deal
                    \t4. View Cart
                    \t5. Checkout
                    \t6. Back to Dashboard
                    """);
            System.out.print(" Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> addToCartProduct();
                case "2" -> addToCartGiftCard();
                case "3" -> addToCartBundleDeal();
                case "4" -> viewCart();
                case "5" -> {
                    boolean continueShopping = processTransaction();

                    if (!continueShopping) {
                        backToShopping = true;
                    }
                }
                case "6" -> {
                    return;
                }
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Displays product list and allows the customer to add an available product to cart.
     * <p>Out-of-stock products cannot be added.</p>
     */
    private void addToCartProduct() {
        System.out.println("""
                \n*****
                PRODUCT LIST
                ------------------------------------------------------------
                Code     Name                          Price(RM)     Stock
                ------------------------------------------------------------""");

        for (Item item : itemList) {
            if (item instanceof Product product) { // Temporary called product, stockQuantity in Product class
                String stockDisplay;

                if (product.getStockQuantity() == 0) {
                    stockDisplay = "OUT OF STOCK";
                } else {
                    stockDisplay = String.valueOf(product.getStockQuantity());
                }

                System.out.printf("%-8s %-28s %10.2f     %-12s\n",
                        item.getItemCode(), item.getItemName(), item.getPrice(), stockDisplay);
            }
        }

        System.out.println("------------------------------------------------------------");

        Item foundItem = null;

        while (foundItem == null) {
            boolean foundCode = false;

            System.out.print(" Enter code (X to go back): ");
            String itemCode = input.nextLine().trim();

            if (itemCode.equalsIgnoreCase("x")) {
                return;
            }

            for (Item item : itemList) {
                if (item instanceof Product product && item.getItemCode().equalsIgnoreCase(itemCode)) {
                    foundCode = true;

                    if (product.getStockQuantity() == 0) {
                        System.out.println("\n Error: This product is OUT OF STOCK.");
                        foundItem = null;
                    } else {
                        foundItem = item;
                    }

                    break;
                }
            }

            if (!foundCode) {
                System.out.println("\n Error: Product not found.\n");
            }
        }

        cart.add(foundItem);
        System.out.println("\nProduct added to cart successfully.");
    }

    /**
     * Displays gift card list and allows the customer to add a gift card to cart.
     */
    private void addToCartGiftCard() {
        System.out.println("""
            \n*****
            GIFT CARD LIST
            --------------------------------------------------------------------------------
            Code     Name                          Price(RM)   Denomination   Expiry Date
            --------------------------------------------------------------------------------""");

        for (Item item : itemList) {
            if (item instanceof GiftCard giftCard) {

                System.out.printf("%-8s %-28s %10.2f %14.2f   %-12s\n", item.getItemCode(), item.getItemName(),
                            item.getPrice(), giftCard.getDenomination(), giftCard.getExpiryDate());
            }
        }

        System.out.println("--------------------------------------------------------------------------------");

        Item foundItem = null;

        while (foundItem == null) {
            boolean foundCode = false;

            System.out.print(" Enter gift card code add to cart (X to go back): ");
            String itemCode = input.nextLine().trim();

            if (itemCode.equalsIgnoreCase("X")) {
                    return;
            }

            for (Item item : itemList) {
                if (item instanceof GiftCard && item.getItemCode().equalsIgnoreCase(itemCode)) {
                    foundCode = true;
                    foundItem = item;
                    break;
                }
            }

            if (!foundCode) {
                System.out.println(" Error: Gift card code not found.\n");
            }
        }

        cart.add(foundItem);
        System.out.println("\nGift card added to cart successfully.");

    }

    /**
     * Displays bundle deal list and allows the customer to add a bundle deal to cart.
     */
    private void addToCartBundleDeal() {
        System.out.println("""
        \n*****
        BUNDLE DEAL LIST
        ------------------------------------------------------------------------------------------""");

        System.out.printf("%-5s %-25s %-30s %10s %12s\n",
                "Code", "Name", "Included Items", "Price(RM)", "Discount(%)");

        System.out.println("------------------------------------------------------------------------------------------");

        for (Item item : itemList) {
            if (item instanceof BundleDeal bundleDeal) {
                String[] includedItems = bundleDeal.getIncludedItems().split(",");
                for (int i = 0; i < includedItems.length; i++) {
                    if (i == 0) {
                        // The first line print full info
                        System.out.printf("%-5s %-25s %-30s %10.2f %12d\n",
                                item.getItemCode(),
                                item.getItemName(),
                                includedItems[i].trim(),
                                item.getPrice(),
                                bundleDeal.getBundleDiscount());
                    } else {
                        // The remaining lines print included item only
                        System.out.printf("%-5s %-25s %-30s\n",
                                "",
                                "",
                                includedItems[i].trim());
                    }
                }
                System.out.println("------------------------------------------------------------------------------------------");
            }
        }
        Item foundItem = null;

        while (foundItem == null) {
            boolean foundCode = false;

            System.out.print(" Enter bundle deal code add to cart (X to go back): ");
            String itemCode = input.nextLine().trim();

            if (itemCode.equalsIgnoreCase("X")) {
                return;
            }

            for (Item item : itemList) {
                if (item instanceof BundleDeal && item.getItemCode().equalsIgnoreCase(itemCode)) {
                    foundCode = true;
                    foundItem = item;
                    break;
                }
            }

            if (!foundCode) {
                System.out.println(" Error: Bundle deal code not found.\n");
            }
        }

        cart.add(foundItem);
        System.out.println("\nBundle deal added to cart successfully.");
    }

    /**
     * Displays all items currently added to the customer's cart,
     * including quantity, price, and total amount.
     */
    private void viewCart() {
        if (cart == null || cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        double subtotal = 0.0;

        System.out.println("""
                \n*****
                 YOUR CART
                ====================================
                 Item\t Qty\tPrice(RM)\tAmount
                ------------------------------------""");
        for (Item itemQty : itemList) {
            int quantity = 0;

            for (Item cartItem : cart) {
                if (cartItem.getItemCode().equals(itemQty.getItemCode())) {
                    quantity++;
                }
            }

            if (quantity > 0) {
                double totalQtyPrice = itemQty.getPrice() * quantity;
                subtotal += totalQtyPrice;

                System.out.println(itemQty.getItemName());
                System.out.printf(" %-6s %3d %10.2f %10.2f\n",
                        itemQty.getItemCode(),
                        quantity,
                        itemQty.getPrice(),
                        totalQtyPrice);
            }
        }
        System.out.println("------------------------------------");
        System.out.printf(" %-20s  %10.2f\n", "Subtotal", subtotal);

    }

    /**
     * Processes the customer's transaction by calling the checkout method.
     *
     * @return true if the customer continues shopping, otherwise false.
     */
    @Override
    public boolean processTransaction() {
        return checkout();
    }

    /**
     * Applies discount to the subtotal.
     *
     * @param subtotal The amount before discount.
     * @param discount The discount amount.
     * @return The amount after discount.
     */
    @Override
    public double applyDiscount(double subtotal, double discount) {
        return subtotal - discount;
    }

    /**
     * Handles payment method selection and creates the payment object.
     *
     * @param totalAmount The final amount to be paid.
     * @return The completed payment object, or null if checkout is cancelled.
     */
    private Payment makePayment(double totalAmount) {

        Payment payment;
        String transactionId = "T" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss"));

        while (true) {
            System.out.println("""
                    \n*****
                     SELECT PAYMENT METHOD
                    ====================================""");
            System.out.println("\t1. Cash Payment");
            System.out.println("\t2. Digital Payment");
            System.out.println("\t3. Exit Checkout");
            System.out.print("\n Enter choice: ");
            String paymentChoice = input.nextLine().trim();

            switch (paymentChoice) {
                case "1" -> {
                    boolean backToPaymentMethod = false;
                    do {
                        System.out.print("\n Enter Amount (X to return): RM ");
                        String amountInput = input.nextLine().trim();

                        if (amountInput.equalsIgnoreCase("X")) {
                            backToPaymentMethod = true;
                        } else {
                            try {
                                double cashReceived = Double.parseDouble(amountInput); // User input String turn it into double

                                if (cashReceived < totalAmount) {
                                    System.out.println("Error: Amount is not enough! ");
                                } else {
                                    payment = new CashPayment(transactionId, totalAmount, getCustId(), cashReceived);
                                    transactionHistory.add(payment);
                                    return payment;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Invalid amount input.");
                            }
                        }
                    } while (!backToPaymentMethod);
                }
                case "2" -> {
                    while (true) {
                        System.out.print(" Pay using provider (X to return): ");
                        String provider = input.nextLine().trim().toUpperCase();

                        if (provider.equalsIgnoreCase("X")) {
                            break;
                        }

                        if (provider.isEmpty()) {
                            System.out.println("Error: Payment provider cannot be empty!");
                        } else {
                            payment = new DigitalPayment(transactionId, totalAmount, getCustId(), provider);
                            transactionHistory.add(payment);
                            return payment;
                        }
                    }
                }
                case "3" -> {
                    return null;
                }
                default -> System.out.println("\n Error: Invalid choice. Please try again.\n");
            }
        }
    }

    /**
     * Generates and displays the itemised receipt after successful payment.
     *
     * @param payment The payment object used for this transaction.
     * @param subtotal The subtotal before discount.
     * @param discount The discount amount applied.
     * @param roundingAdj The rounding adjustment amount.
     * @param roundedTotal The final rounded total amount.
     */
    @Override
    public void generateReceipt(Payment payment, double subtotal, double discount, double roundingAdj, double roundedTotal) {
        // Print receipt details
        System.out.println("""
                \n************************************
                
                         SMART RETAIL STORE
                ====================================
                """);
        System.out.println(" Transaction ID: " + payment.getTransactionId());
        System.out.println(" Date: " + payment.getPaymentDate());
        System.out.println(" Time: " + payment.getPaymentTime());
        System.out.println("""
                ------------------------------------
                 Item\t Qty\tPrice(RM)\tAmount
                ------------------------------------""");

        for (Item itemQty : itemList) {
            int quantity = 0;

            for (Item cartItem : cart) {
                if (cartItem.getItemCode().equals(itemQty.getItemCode())) {
                    quantity++;
                }
            }

            if (quantity > 0) {
                double totalQtyPrice = itemQty.getPrice() * quantity;

                System.out.println(itemQty.getItemName());
                System.out.printf(" %-6s %3d %10.2f %10.2f\n",
                        itemQty.getItemCode(),
                        quantity,
                        itemQty.getPrice(),
                        totalQtyPrice);
            }
        }

        System.out.println("------------------------------------");
        System.out.printf(" %-20s  %10.2f\n", "Subtotal", subtotal);
        System.out.printf(" %-20s  %10.2f\n", "Discount", discount);
        System.out.printf(" %-20s  %10.2f\n", "Rounding Adjustment", roundingAdj);
        System.out.printf(" %-20s  %10.2f\n", "TOTAL", roundedTotal);
        System.out.println("====================================");
        System.out.println(" Payment Method : " + payment.getPaymentMethod());
        System.out.println("------------------------------------");
        payment.displayPaymentDetails();
        System.out.println("""
                ====================================
                   Thank you. Please Come Again.
                ====================================
                """);
    }

    /**
     * Performs the checkout process, including subtotal calculation,
     * gift card discount validation, rounding adjustment, payment, receipt generation,
     * stock deduction, and cart clearing.
     *
     * @return true if the customer continues shopping, otherwise false.
     */
    private boolean checkout() {

        if (cart == null || cart.isEmpty()) {
            System.out.println("\n Error: Your cart is empty. Please add item before checkout.\n");
            return true;
        }

        double subtotal = 0.0;
        double totalQtyPrice;
        int quantity;
        double discount = 0.0;
        double totalAmount;

        for (Item itemPrice : cart) {
            subtotal += itemPrice.getPrice();
        }

        totalAmount = subtotal;

        System.out.println("""
                \n*****
                 CHECKOUT SUMMARY
                ====================================
                 Item\t Qty\tPrice(RM)\tAmount
                ------------------------------------""");
        for (Item itemQty : itemList) {
            quantity = 0; //reset every item

            for (Item cartItem : cart) {
                if (cartItem.getItemCode().equals(itemQty.getItemCode())) {
                    quantity++;
                }
            }

            if (quantity > 0) {
                totalQtyPrice = itemQty.getPrice() * quantity;

                System.out.println(itemQty.getItemName());
                System.out.printf(" %-6s %3d %10.2f %10.2f\n", itemQty.getItemCode(), quantity, itemQty.getPrice(), totalQtyPrice);
            }
        }

        System.out.println("====================================");
        System.out.printf(" %-20s %11.2f\n", "Subtotal", subtotal);
        System.out.println("====================================");
        System.out.print("Enjoy exclusive discount now!");

        if (Utility.promptYesNo(input, "Have a gift card?")) {
            boolean discountApplied = false;
            while (!discountApplied) {
                System.out.print("Enter the code(Press enter to skip): ");
                String enterCode = input.nextLine().toUpperCase().trim();

                if (enterCode.isEmpty()) {
                    System.out.println("Continue to payment.");
                    break;
                }

                GiftCard giftCard = null;

                for (Item item : itemList) {
                    if (item instanceof GiftCard gc) {

                        if (gc.getItemCode().equalsIgnoreCase(enterCode)) {
                            giftCard = gc;
                            break;
                        }
                    }
                }
                if (giftCard == null) {
                    System.out.println("Invalid gift card code.");
                    continue;
                }
                System.out.println("*****");
                System.out.println("\n " + giftCard.getItemName());
                System.out.println("------------------------------------");
                System.out.printf(" %-15s %10s\n", "Code", giftCard.getItemCode());
                System.out.printf(" %-15s RM %7.2f\n", "Denomination", giftCard.getDenomination());
                System.out.printf(" %-15s %10s\n", "Expiry Date", giftCard.getExpiryDate());
                System.out.println(" ------------------------------------");

                String expiredDate = giftCard.getExpiryDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate expiry = LocalDate.parse(expiredDate, formatter);
                LocalDate today = LocalDate.now();

                if (expiry.isBefore(today)) {
                    System.out.println("Sorry, gift card already expired.");
                    System.out.println("\nEnter another gift card.");
                    continue;
                }

                discount = giftCard.getDenomination();
                totalAmount = applyDiscount(subtotal, discount);

                System.out.printf("\tRM %.0f discount applied.\n", discount);

                discountApplied = true;
            }

        }
        // get rounding adjustment from utility class
        double[] rounding = Utility.roundingAdjustment(totalAmount);
        double roundingAdj = rounding[0];
        double roundedTotal = rounding[1];

        System.out.println("====================================");
        System.out.printf(" %-20s %11.2f\n", "Discount", discount);
        System.out.printf(" %-20s %11.2f\n", "Total Before Round", totalAmount);
        System.out.printf(" %-20s %11.2f\n", "Rounding Adj", roundingAdj);
        System.out.printf(" %-20s %11.2f\n", "Total Amount", roundedTotal);
        System.out.println("====================================");

        Payment payment = makePayment(roundedTotal);

        if (payment == null) {
            System.out.println("\nPayment failed. Checkout cancelled.");
            return true;
        }

        generateReceipt(payment, subtotal, discount, roundingAdj, roundedTotal);

        for (Item cartItem : cart) {
            if (cartItem instanceof Product product) {
                product.setStockQuantity(product.getStockQuantity() - 1); // deduct stock after checkout
            }
        }

        // Clear cart
        cart.clear();

        return Utility.promptYesNo(input, "Continue Shopping?");
    }

    /**
     * Displays transaction records that belong to the current customer only.
     */
    private void viewOrderRecords() {
        if (transactionHistory == null || transactionHistory.isEmpty()) {
            System.out.println("\n No transaction records found!\n");
            return;
        }

        ArrayList<Payment> userTransaction = new ArrayList<>();

        for (Payment payment : transactionHistory) {
            if (payment.getCustId().equalsIgnoreCase(getCustId())) {
                userTransaction.add(payment);
            }
        }

        if (userTransaction.isEmpty()) {
            System.out.println(" No order records found.\n");
            return;
        }

        System.out.println("""
                \n*****
                 ORDER/TRANSACTION RECORDS
                ====================================""");

        Payment.displayTransactionRecord(userTransaction);
    }

    /**
     * Displays the manage account menu and allows the customer to update
     * name, username, password, contact number, and email.
     */
    private void manageAccount() {
        boolean backToMenu = false;

        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \tMANAGE ACCOUNT
                    ====================================
                    \t1. View Account Details
                    \t2. Update Name
                    \t3. Update Username
                    \t4. Update Password
                    \t5. Update Contact number
                    \t6. Update Email
                    \t7. Back to Dashboard
                    """);

            System.out.print(" Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> viewAccount();
                case "2" -> {
                    boolean isValid;
                    String newName;

                    do {
                        System.out.print("\nNew name: ");
                        newName = input.nextLine().trim();

                        if (newName.isEmpty()) {
                            System.out.println("Error: Name cannot be empty!");
                            isValid = false;
                        } else {
                            setName(newName);
                            System.out.println("Name has been updated.");
                            isValid = true;
                        }
                    } while (!isValid);
                }
                case "3" -> {
                    boolean isValid;
                    String newUsername;

                    do {
                        System.out.print("\nNew Username: ");
                        newUsername = input.nextLine().trim();

                        if (newUsername.isEmpty()) {
                            System.out.println("Error: Username cannot be empty.");
                            isValid = false;
                        } else if (Utility.isUsernameDuplicate(customerList, newUsername)) {
                            System.out.println("Error: Username already exists!");
                            isValid = false;
                        } else {
                            setUsername(newUsername);
                            System.out.println(" Username has been updated.");
                            isValid = true;
                        }
                    } while (!isValid);
                }
                case "4" -> {
                    boolean isValid;
                    String newPassword;

                    do {
                        System.out.print("\nNew Password: ");
                        newPassword = input.nextLine().trim();

                        if (newPassword.isEmpty()) {
                            System.out.println("Error: Password cannot be empty.");
                            isValid = false;
                        } else {
                            setPassword(newPassword);
                            System.out.println(" Password has been updated.");
                            isValid = true;
                        }
                    } while (!isValid);
                }
                case "5" -> {
                    boolean isValid;
                    String newContactNo;

                    do {
                        System.out.print("\nNew Contact (e.g: 0123456789): ");
                        newContactNo = input.nextLine().trim();

                        if (Utility.isContactNumberDuplicate(customerList, newContactNo)) {
                            System.out.println("Error: Contact number already exists!");
                            isValid = false;
                        } else {
                            isValid = Customer.isValidContactNo(newContactNo);

                            if (isValid) {
                                setContactNo(newContactNo);
                                System.out.println(" Contact number has been updated.");
                            }
                        }
                    } while (!isValid);
                }
                case "6" -> {
                    boolean isValid;
                    String newEmail;

                    do {
                        System.out.print("\nNew Email (e.g: user@gmail.com): ");
                        newEmail = input.nextLine().trim();

                        if (Utility.isEmailDuplicate(customerList, newEmail)) {
                            System.out.println("Error: Email already exists!");
                            isValid = false;
                        } else {
                            isValid = Customer.isValidEmail(newEmail);

                            if (isValid) {
                                setEmail(newEmail);
                                System.out.println(" Email has been updated.");
                            }
                        }
                    } while (!isValid);
                }
                case "7" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Displays the current customer's account details.
     */
    private void viewAccount() {
        System.out.println("""
                \n*****
                ACCOUNT DETAILS
                ====================================""");
        System.out.println(" Cust ID    : " + getCustId());
        System.out.println(" Name       : " + getName());
        System.out.println(" Username   : " + getUsername());
        System.out.println(" Contact No : " + getContactNo());
        System.out.println(" Email      : " + getEmail());
        System.out.println("====================================");
    }
}