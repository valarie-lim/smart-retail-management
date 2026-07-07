import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents a staff who can access and manage the smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from User class.</p>
 */
public class Staff extends User implements Transactable {
    private final String staffId;
    private final String position;
    private ArrayList<Customer> customerList;
    private ArrayList<Item> itemList;
    private final ArrayList<Payment> transactionHistory;

    /**
     * Constructs a staff object.
     * @param name The name inherited from User class.
     * @param username The username inherited from User class.
     * @param password The password inherited from User class.
     * @param staffId The unique id of the staff.
     * @param position The position of the staff.
     * @param customerList The list that contains customer's details.
     * @param itemList The list that contains all the items including products, gift cards, and bundle deals.
     * @param transactionHistory The list that contains all the customer transaction records.
     */
    public Staff(String name, String username, String password, String staffId, String position, ArrayList<Customer> customerList, ArrayList<Item> itemList, ArrayList<Payment> transactionHistory) {
        super(name, username, password);
        this.staffId = staffId;
        this.position = position;
        this.customerList = customerList;
        this.itemList = itemList;
        this.transactionHistory = transactionHistory;
    }

    /**
     * Displays login successful message and redirects the staff to their account dashboard.
     * @param input The Scanner object used to read user input.
     */
    @Override
    public void onLoginSuccess(Scanner input) {
        this.input = input;
        System.out.println("\n Staff login successful.\n");
        staffDashboard();
    }

    /**
     * Displays staff dashboard, allowing staff to perform their task by navigating across each management.
     */
    private void staffDashboard() {
        boolean isContinue = true;

        while (isContinue) {
            System.out.println("\n====================================");
            System.out.println("\tHi, " + getName() + "\t\t\t");
            System.out.println("====================================");
            System.out.println("""
                      What do you want to do?
                    \t1. Manage Item
                    \t2. Manage Customer
                    \t3. Manage Transaction Record
                    \t4. My Account
                    \t5. Logout
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> manageItem();
                case "2" -> manageCustomer();
                case "3" -> processTransaction();
                case "4" -> manageAccount();
                case "5" -> {
                    if (Utility.promptYesNo(input, "Confirm to logout?")) {
                        isContinue = false;
                    }
                }
                default -> System.out.println("\n*****\n Error: Invalid input.\n*****\n");
            }
        }
    }

    /**
     * Displays the item management menu for staff to manage different types of items.
     */
    private void manageItem() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \tITEMS MANAGEMENT
                    ====================================
                    \t1. Manage Products
                    \t2. Manage Gift Cards
                    \t3. Manage Bundle Deals
                    \t4. Back to Dashboard
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> manageProduct();
                case "2" -> manageGiftCard();
                case "3" -> manageBundleDeal();
                case "4" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Displays the product management menu for staff to view, add, search, and update products.
     */
    private void manageProduct() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \tPRODUCTS MANAGEMENT
                    ====================================
                    \t1. View Products
                    \t2. Add New Products
                    \t3. Search & Update Products
                    \t4. Back to Item Management
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> viewProduct(itemList);
                case "2" -> addNewProduct(itemList);
                case "3" -> updateProduct(itemList);
                case "4" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Allows staff to view all products.
     * @param itemList The list of existing items.
     */
    private void viewProduct(ArrayList<Item> itemList) {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \t View Product
                    ====================================
                    \t1. Display All Products
                    \t2. Sort by Category
                    \t3. Search & Update Products
                    \t4. Back to Product Management
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> displayAllProducts(itemList);
                case "2" -> sortByCategory(itemList);
                case "3" -> updateProduct(itemList);
                case "4" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Allows staff to display all the products.
     * @param itemList The list of existing items.
     */
    private void displayAllProducts(ArrayList<Item> itemList){
        System.out.println("""
                \n*****
                 ALL PRODUCTS
                ============================================================""");
        System.out.printf("%-6s %-30s %-10s %-12s\n",
                "Code", "Name", "Price(RM)", "Stock");
        System.out.println("------------------------------------------------------------");
        boolean found = false;

        for (Item item : itemList) {
            if (item instanceof Product product) {
                found = true;
                System.out.printf("%-6s %-30s\t%-10.2f %-12s\n",
                        item.getItemCode(), item.getItemName(), item.getPrice(), product.getStockQuantity());

                System.out.println("------------------------------------------------------------");
            }
        }
        if (!found) {
            System.out.println("\n*****\nNo product found.\n");
        }
    }

    /**
     * Allows staff to sort the item based on category.
     * @param itemList The list of existing items.
     */
    private void sortByCategory(ArrayList<Item> itemList){
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n*****
                     SEARCH PRODUCT
                    ====================================""");
            System.out.print(" Enter Category: ");
            String sortCategory = input.nextLine().trim();

            if (sortCategory.equalsIgnoreCase("X")) {
                backToMenu = true;
                continue;
            }
            System.out.println("\n*****");
            System.out.println(" Category: " + sortCategory);
            System.out.println("====================================================");
            System.out.printf("%-6s %-25s %-10s %-12s\n",
                    "Code", "Name", "Price(RM)", "Stock");
            System.out.println("----------------------------------------------------");
            boolean found = false;

            for (Item item : itemList) {
                if (item instanceof Product product && product.getCategory().equalsIgnoreCase(sortCategory)) {
                    found = true;
                    System.out.printf("%-6s %-25s\t%-10.2f %-12s\n",
                            item.getItemCode(), item.getItemName(), item.getPrice(), product.getStockQuantity());

                    System.out.println("----------------------------------------------------");
                }
            }
            if (!found) {
                System.out.println("\n*****\nNo product found.\n");
                return;
            }
            return;
        }
    }

    /**
     * Allows staff to add new product.
     * @param itemList The list of existing items.
     */
    private void addNewProduct(ArrayList<Item> itemList) {
        System.out.println("""
                \n*****
                 ADD NEW PRODUCT
                ====================================""");
        String[] data = Utility.inputItemCommonDetails(input, itemList, "P");
        String newItemCode = data[0];
        String newItemName = data[1];
        double newPrice = Double.parseDouble(data[2]);

        String newCategory;
        while (true) {
            System.out.printf("%-20s: ", " Category");
            String inputCategory = input.nextLine().toUpperCase().trim();

            if (inputCategory.isEmpty()) {
                System.out.println("Error: Category cannot be empty!");
            } else {
                newCategory = inputCategory;
                break;
            }
        }

        int newStockQuantity;
        while (true) {
            System.out.printf("%-20s: ", " Stock Quantity");
            try {
                newStockQuantity = Integer.parseInt(input.nextLine());
                if (newStockQuantity < 0) {
                    System.out.println("Error: Stock quantity must be greater than 0!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer!");
            }
        }

        itemList.add(new Product(newItemCode, newItemName, newPrice, newCategory, newStockQuantity));

        System.out.println("""
                \nProduct added successfully
                ------------------------------------""");
        System.out.printf("Code: %s \nName: %s\n", newItemCode, newItemName);
    }

    /**
     * Allows staff to search & update product.
     * @param itemList The list of existing items.
     */
    private void updateProduct(ArrayList<Item> itemList) {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                \n*****
                 SEARCH & UPDATE PRODUCT
                ====================================""");
            System.out.print(" Enter Item Code: ");
            String searchCode = input.nextLine().trim();

            if (searchCode.isEmpty()) continue;

            Item foundItem = Utility.findItemByCode(itemList, searchCode);

            if (foundItem instanceof Product product) {
                System.out.println("\n\tProduct Code  : " + foundItem.getItemCode());
                System.out.println("\tName\t\t  : " + foundItem.getItemName());
                System.out.printf("\tPrice (RM)\t  : %.2f\n", foundItem.getPrice());
                System.out.println("\tCategory\t  : " + product.getCategory());
                System.out.println("\tStock Quantity: " + product.getStockQuantity());

                System.out.println("""
                    ------------------------------------
                    \t1. Update Product Name
                    \t2. Update Price
                    \t3. Update Category
                    \t4. Update Stock Quantity
                    \t5. Back to Product Menu
                    """);
                System.out.print("  Enter choice: ");
                String choice = input.nextLine().trim();

                switch (choice) {
                    case "1" -> {
                        System.out.print("\nEnter Name (press enter to cancel): ");
                        String newName = input.nextLine().toUpperCase().trim();

                        if (!newName.isEmpty()) {
                            foundItem.setItemName(newName);
                            System.out.println(" Name has been updated.");
                        }
                        else{
                            System.out.println("******\n Name update has been cancelled.");
                        }
                        return;
                    }
                    case "2" -> {
                        boolean isValid = false;
                        while(!isValid){
                            System.out.print("\nEnter Price (press enter to cancel): ");
                            String priceInput = input.nextLine().trim();

                            if (priceInput.isEmpty()) {
                                System.out.println("******\n Price update has been cancelled.");
                                return;
                            }
                            try {
                                double newPrice = Double.parseDouble(priceInput);

                                if (newPrice > 0) {
                                    foundItem.setPrice(newPrice);
                                    System.out.println(" Price updated.");
                                    isValid = true;
                                } else {
                                    System.out.println(" Error: Price must be greater than 0!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Error: Please enter a valid price!");
                            }
                        }
                        return;
                    }
                    case "3" -> {
                        boolean isValid = false;
                        while(!isValid) {
                            System.out.print("\nEnter Category (press enter to cancel): ");
                            String newCategory = input.nextLine().toUpperCase().trim();

                            if (!newCategory.isEmpty()) {
                                product.setCategory(newCategory);
                                System.out.println(" Category updated.");
                                isValid = true;
                            } else {
                                System.out.println("******\n Category update has been cancelled.");
                            }
                        }
                    }
                    case "4" -> {
                        boolean isValid = false;
                        while(!isValid) {
                            System.out.print("\nEnter Stock Qty (press enter to cancel): ");
                            String stockInput = input.nextLine().trim();

                            if (stockInput.isEmpty()) {
                                System.out.println("******\n Stock update has been cancelled.");
                                return;
                            }
                            try {
                                int newStock = Integer.parseInt(stockInput);

                                if (newStock > 0) {
                                    product.setStockQuantity(newStock);
                                    System.out.println(" Stock updated.");
                                    isValid = true;
                                } else {
                                    System.out.println(" Error: Stock quantity must be greater than 0!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(" Error: Please enter a valid integer!");
                            }
                        }
                    }
                    case "5" -> {
                        return;
                    }
                    default -> System.out.println("\n Error: Invalid choice.\n");
                }
            } else if (foundItem != null) {
                System.out.println(" Found '" + searchCode + "', but it is NOT a Product.");
                if (!Utility.promptYesNo(input, "Try another code?")) return;
            } else {
                System.out.println(" Error: Product Code '" + searchCode + "' not found.");
                if (!Utility.promptYesNo(input, "Try again?")) {
                    backToMenu = true;
                }
            }
        }
    }

    /**
     * Displays the gift card management menu for staff to view, add, gift cards.
     */
    private void manageGiftCard() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                \n====================================
                \tGIFT CARDS MANAGEMENT
                ====================================
                \t1. View Gift Cards
                \t2. Add New Gift Cards
                \t3. Back to Item Management
                """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> viewGiftCard(itemList);
                case "2" -> addNewGiftCard(itemList);
                case "3" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Allows staff to view all gift cards.
     * @param itemList The list of existing items.
     */
    private void viewGiftCard(ArrayList<Item> itemList) {
        System.out.println("""
                \n*****
                 Gift Card
                ============================================================""");
        System.out.printf("%-6s %-20s %-10s %-10s %-12s\n",
                "Code", "Name", "Price(RM)", "Value(RM)", "Exp Date");
        System.out.println("------------------------------------------------------------");
        boolean found = false;

        for (Item item : itemList) {
            if (item instanceof GiftCard giftcard) {
                found = true;
                System.out.printf("%-6s %-20s\t%-10.2f %-10.2f %-12s\n",
                        item.getItemCode(), item.getItemName(), item.getPrice(), giftcard.getDenomination(), giftcard.getExpiryDate());

                System.out.println("------------------------------------------------------------");
            }
        }
        if (!found) {
            System.out.println("\n*****\nNo gift card found.\n");
        }
    }

    /**
     * Allows staff to add new gift card.
     * @param itemList The list of existing items.
     */
    private void addNewGiftCard(ArrayList<Item> itemList) {
        System.out.println("""
            \n*****
             ADD NEW GIFT CARD
            ====================================""");
        String[] data = Utility.inputItemCommonDetails(input, itemList, "G");
        String newItemCode = data[0];
        String newItemName = data[1];
        double newPrice = Double.parseDouble(data[2]);

        String newExpiryDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean isValid = false;

        do {
            System.out.printf("%-20s: ", " Exp Date(dd-MM-yyyy)");
            newExpiryDate = input.nextLine().trim();

            try {
                LocalDate.parse(newExpiryDate, formatter);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Please use dd-MM-yyyy.");
            }

        } while (!isValid);

        itemList.add(new GiftCard(newItemCode, newItemName, newPrice, newPrice, newExpiryDate));

        System.out.println("""
                \nGift Card added successfully
                ------------------------------------""");
        System.out.printf("Code: %s \nName: %s\n", newItemCode, newItemName);
    }

    /**
     * Displays the bundle management menu for staff to view and add bundle.
     */
    private void manageBundleDeal() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                \n====================================
                \tBUNDLE DEAL MANAGEMENT
                ====================================
                \t1. View Bundles
                \t2. Add New Bundle
                \t3. Back to Item Management
                """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> viewBundle(itemList);
                case "2" -> addNewBundle(itemList);
                case "3" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Allows staff to view all bundle deals.
     * @param itemList The list of existing items.
     */
    private void viewBundle(ArrayList<Item> itemList) {
        System.out.println("""
                \n*****
                 Bundle Deal
                ==========================================================================================""");
        System.out.printf("%-5s %-25s %-30s %10s %12s\n",
                "Code", "Name", "Included Items", "Price(RM)", "Discount(%)");
        System.out.println("------------------------------------------------------------------------------------------");
        boolean found = false;

        for (Item item : itemList) {
            if (item instanceof BundleDeal bundleDeal) {
                found = true;
                String[] includedItems = bundleDeal.getIncludedItems().split(",");
                for (int i = 0; i < includedItems.length; i++) {
                    if (i == 0) {
                        System.out.printf("%-5s %-25s %-30s %10.2f %12d\n",
                                item.getItemCode(),
                                item.getItemName(),
                                includedItems[i].trim(),
                                item.getPrice(),
                                bundleDeal.getBundleDiscount());
                    } else {
                        System.out.printf("%-5s %-25s %-30s\n",
                                "",
                                "",
                                includedItems[i].trim());
                    }
                }
                System.out.println("------------------------------------------------------------------------------------------");
            }
        }
        if (!found) {
            System.out.println("\n*****\nNo gift card found.\n");
        }
    }

    /**
     * Allows staff to add new bundle deal.
     * @param itemList The list of existing items.
     */
    private void addNewBundle(ArrayList<Item> itemList) {
        System.out.println("""
            \n*****
             ADD NEW BUNDLE DEAL
            ====================================""");
        String[] data = Utility.inputItemCommonDetails(input, itemList, "B");
        String newItemCode = data[0];
        String newItemName = data[1];
        double newPrice = Double.parseDouble(data[2]);

        String newIncludedItems;
        while (true) {
            System.out.printf("%-20s: ",
                    " Included Item (separated each item with comma)");
            String inputItems = input.nextLine().toUpperCase().trim();

            if (inputItems.isEmpty()) {
                System.out.println("Error: Included item cannot be empty!");
            } else {
                newIncludedItems = inputItems;
                break;
            }
        }

        int newBundleDiscount;
        while (true) {
            System.out.printf("%-20s: ", " Bundle Discount(%)");
            try {
                newBundleDiscount = Integer.parseInt(input.nextLine());

                if (newBundleDiscount < 0) {
                    System.out.println("Error: Discount must be greater than 0!");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer!");
            }
        }

        itemList.add(new BundleDeal(newItemCode, newItemName, newPrice, newBundleDiscount, newIncludedItems));

        System.out.println("""
                \nBundle added successfully
                ------------------------------------""");
        System.out.printf("Code: %s \nName: %s\n", newItemCode, newItemName);
    }

    /**
     * Displays the customer management menu for staff to view, register, search, and update customer.
     */
    private void manageCustomer() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \tCUSTOMER MANAGEMENT
                    ====================================
                    \t1. View ALL Customers
                    \t2. Register New Customer
                    \t3. Search & Update Customer
                    \t4. Back to Dashboard
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> viewCustomer(customerList);
                case "2" -> registerCustomer(customerList);
                case "3" -> updateCustomer(customerList);
                case "4" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Displays customer details for staff.
     * @param customerList The list of existing customers.
     */
    private void viewCustomer(ArrayList<Customer> customerList){
        System.out.println("""
                \n*****
                 CUSTOMER LIST
                ====================================""");
        if (customerList.isEmpty()) {
            System.out.println("\n*****\nNo Customer found.\n");
        } else {
            System.out.printf(" %-7s  %-11s  %-15s  %-20s  %-11s \n",
                    "Cust ID", "Contact No", "Name", "Email", "Acc Status");
            System.out.println("--------------------------------------------------------------------------------");

            for (Customer customer : customerList) {
                System.out.printf(" %-7s  %-11s  %-15s  %-20s ",
                        customer.getCustId(),
                        customer.getContactNo(),
                        customer.getName(),
                        customer.getEmail());

                if (customer.getStatus()) {
                    System.out.println(" Active");
                } else {
                    System.out.println(" Non-Active");
                }
                System.out.println("--------------------------------------------------------------------------------");
            }
        }
    }

    /**
     * Allows staff to register a new customer using customer's name, contact number, and email.
     * <p>The contact number will be used as the customer's user ID, while the email will be
     * used as the customer's initial login username.</p>
     *
     * @param customerList The list of existing customers.
     */
    private void registerCustomer(ArrayList<Customer> customerList) {
        System.out.println("""
                \n*****
                 NEW CUSTOMER REGISTRATION
                ====================================""");

        System.out.print(" Name : ");
        String newName = input.nextLine().trim();

        boolean isValid;

        // Check contact number input
        String newContactNo;
        do {
            System.out.print(" Contact (e.g: 0123456789): ");
            newContactNo = input.nextLine().trim();

            if (Utility.isContactNumberDuplicate(customerList, newContactNo)) { // Check if contact duplicate
                System.out.println("Error: Contact number already exists!");
                isValid = false;
            } else {
                isValid = Customer.isValidContactNo(newContactNo); // Validate contact input
            }
        } while (!isValid);

        // Check email input
        String newEmail;
        do {
            System.out.print(" Email (e.g: user@gmail.com): ");
            newEmail = input.nextLine().trim();

            if (Utility.isEmailDuplicate(customerList, newEmail)) { // Check if email duplicate
                System.out.println("Error: Email already exists!");
                isValid = false;
            } else {
                isValid = Customer.isValidEmail(newEmail); // Validate email input
            }
        } while (!isValid);

        //use name + random number to generate a temporary password
        Random randomPass = new Random();
        int number = randomPass.nextInt(1000000) + 1;
        String formattedPass = String.format("%06d", number);
        String defaultPass = newName + formattedPass;

        Customer newCustomer = new Customer(
                newName,
                newEmail,   //use email as the username for new customer registration
                defaultPass,
                newContactNo,
                newEmail,
                itemList,
                transactionHistory,
                customerList
        );

        customerList.add(newCustomer);

        System.out.println("""
                \n Customer registered successfully!
                ------------------------------------""");
        System.out.println(" Customer ID: " + newCustomer.getCustId());
        System.out.println(" Customer Username: " + newEmail);
        System.out.println(" Temporary Password: " + defaultPass + "\n");

    }

    /**
     * Allows staff to search for customers and assist them in updating their basic information, including name, contact number, and email.
     * <p>Updating the contact number will also update the customer's user ID,
     * while updating the email will not change the customer's username.</p>
     *
     * @param customerList The list of existing customers.
     */
    private void updateCustomer(ArrayList<Customer> customerList) {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n*****
                     SEARCH & UPDATE CUSTOMER
                    ====================================""");
            System.out.print(" Enter Contact No: ");
            String searchContact = input.nextLine();

            //search if username exist first
            boolean found = false;
            for (Customer customer : customerList) {
                if (customer.getContactNo().equalsIgnoreCase(searchContact)) {
                    found = true;
                    String status = customer.getStatus() ? "Active" : "Deactivated";
                    System.out.println("""
                            \n  Customer Details
                            ------------------------------------""");
                    System.out.println("\tCust ID\t: " + customer.getCustId());
                    System.out.println("\tName\t: " + customer.getName());
                    System.out.println("\tContact\t: " + customer.getContactNo());
                    System.out.println("\tEmail\t: " + customer.getEmail());
                    System.out.println("\tAcc Status: " + status);
                    System.out.println("""
                            ------------------------------------
                            \t1. Update Customer Name
                            \t2. Update Contact No
                            \t3. Update Email
                            \t4. Toggle Status (Active/Deactive)
                            \t5. Back to Customer Menu
                            """);
                    System.out.print("  Enter choice: ");
                    String choice = input.nextLine().trim();

                    switch (choice) {
                        case "1" -> {
                            System.out.print("\nNew Name (enter to skip): ");
                            String newName = input.nextLine();

                            // Only update if input not empty
                            if (!newName.isEmpty()) {
                                customer.setName(newName);
                            }
                            System.out.println(" Name has been updated.");
                            backToMenu = true;
                        }

                        case "2" -> {
                            boolean isValid;
                            String newContactNo;

                            do {
                                System.out.print("\nNew Contact No.\n(e.g: 0109988776) : ");
                                newContactNo = input.nextLine().trim();

                                if (Utility.isContactNumberDuplicate(customerList, newContactNo)) {
                                    System.out.println("Error: Contact number already exists!");
                                    isValid = false;
                                } else {
                                    isValid = Customer.isValidContactNo(newContactNo);

                                    if (isValid) {
                                        customer.setContactNo(newContactNo);
                                        System.out.println(" Contact number has been updated.");
                                        backToMenu = true;
                                    }
                                }
                            } while (!isValid);
                        }

                        case "3" -> {
                            boolean isValid;
                            String newEmail;

                            do {
                                System.out.print("\nNew Email\n (e.g: example@gmail.com) : ");
                                newEmail = input.nextLine().trim();

                                if (Utility.isEmailDuplicate(customerList, newEmail)) {
                                    System.out.println("Error: Email already exists!");
                                    isValid = false;
                                } else {
                                    isValid = Customer.isValidEmail(newEmail);

                                    if (isValid) {
                                        customer.setEmail(newEmail);
                                        System.out.println(" Email has been updated.");
                                        backToMenu = true;
                                    }
                                }
                            } while (!isValid);
                        }

                        case "4" -> {
                            customer.toggleStatus();
                            String newStatus;
                            if (customer.getStatus()) {
                                newStatus = "Active";
                            } else {
                                newStatus = "Inactive";
                            }
                            System.out.println(" Status updated to: " + newStatus);
                            backToMenu = true;
                        }

                        case "5" -> backToMenu = true;
                        default -> System.out.println("\n Error: Invalid choice.\n");
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println(" Error: Contact '" + searchContact + "' not found.");
                break;
            }
        }
    }

    /**
     * Overrides the method in Transactable interface to open the transaction management menu for staff.
     * @return true after the menu execution is completed.
     */
    @Override
    public boolean processTransaction() {
        manageTransaction();
        return true;
    }

    /**
     * Displays the sale record management menu for staff to view, search, cancel and refund transaction.
     */
    private void manageTransaction() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \tTRANSACTION MANAGEMENT
                    ====================================
                    \t1. View Transaction History
                    \t2. Search, Cancel & Refund Transaction
                    \t3. Back to Dashboard
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> viewTransactionHistory(transactionHistory);
                case "2" -> cancelAndRefundTransaction(transactionHistory);
                case "3" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }

    /**
     * Displays all transaction records.
     * @param transactionHistory The list of transactions records generated from customer orders.
     */
    private void viewTransactionHistory(ArrayList<Payment> transactionHistory) {
        System.out.println("""
                \n*****
                 ALL TRANSACTION RECORDS
                ====================================""");
        if (transactionHistory.isEmpty()) {
            System.out.println("\n*****\nNo transactions found.\n");
        } else {
            Payment.displayTransactionRecord(transactionHistory);
        }
    }

    /**
     * Allows staff to search for a transaction ID, as well as cancel and refund that transaction.
     * @param transactionHistory The list of transactions records generated from customer orders.
     */
    private void cancelAndRefundTransaction(ArrayList<Payment> transactionHistory) {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n*****
                     SEARCH TRANSACTION RECORDS
                    ====================================""");
            System.out.print(" Enter Transaction ID: ");
            String searchTransId = input.nextLine().trim();

            // Search if transaction id exist first
            boolean found = false;

            for (Payment payment : transactionHistory) {
                if (payment.getTransactionId().equalsIgnoreCase(searchTransId)) {

                    found = true;

                    System.out.println("\n\tTransaction ID: " + payment.getTransactionId());
                    System.out.println("\tDate\t: " + payment.getPaymentDate());
                    System.out.printf("\tAmount\t: %.2f\n", payment.getTotalAmount());
                    System.out.println("\tMethod\t: " + payment.getPaymentMethod());

                    if (payment.getTransactionStatus()) {
                        System.out.println("\tStatus\t: Completed");
                    } else {
                        System.out.println("\tStatus\t: Cancelled/Refunded");
                    }

                    System.out.println("""
                            ------------------------------------
                            \t1. Cancel & Refund
                            \t2. Back to Transaction Management Menu
                            """);
                    System.out.print("  Enter choice: ");
                    String choice = input.nextLine().trim();

                    switch (choice) {
                        case "1" -> {
                            if (!payment.getTransactionStatus()) {
                                System.out.println(" Transaction already cancelled.");
                            } else {
                                payment.cancelTransaction();
                                System.out.println(" Transaction updated: cancelled and refunded.");
                            }
                            backToMenu = true;
                        }

                        case "2" -> backToMenu = true;

                        default -> System.out.println("\n Error: Invalid choice.\n");
                    }
                    break;
                }
            }
            if (!found) {
                System.out.println(" Error: Transaction ID'" + searchTransId + "' not found.");
                break;
            }
        }
    }

    /**
     * Overrides the applydiscount() method from Transactable interface.
     * <p>Staff accounts do not apply discounts during transactions.</p>
     *
     * @param subtotal The subtotal amount before discount.
     * @param discountAmount The discount amount after applying gift card.
     * @return 0 because staff transactions do not apply discounts.
     */
    @Override
    public double applyDiscount(double subtotal, double discountAmount) {
        return 0;
    }

    /**
     * Generates a transaction receipt.
     * @param payment The payment information of the transaction.
     * @param subtotal The subtotal amount before discount.
     * @param discount The discount amount after applying gift card.
     * @param roundingAdj The rounding adjustment amount.
     * @param roundedTotal The final rounded total amount.
     */
    @Override
    public void generateReceipt(Payment payment, double subtotal, double discount, double roundingAdj, double roundedTotal) {
        System.out.println(" Receipt generated by staff: " + getName());
        // The transaction receipt usually generated by customers themselves
    }

    /**
     * Displays the account management menu for staff to update their own username and password.
     */
    private void manageAccount() {
        boolean backToMenu = false;
        while (!backToMenu) {
            System.out.println("""
                    \n====================================
                    \tMY ACCOUNT MANAGEMENT
                    ====================================""");
            System.out.println("\tStaff ID: " + staffId);
            System.out.println("\tPosition: " + position);
            System.out.println("\tName    : " + getName());
            System.out.println("\tUsername: " + getUsername());
            System.out.println("\tPassword: " + "********");
            System.out.println("""
                    ------------------------------------
                    \t1. Update Username
                    \t2. Update Password
                    \t3. Back to Dashboard
                    """);
            System.out.print("  Enter choice: ");
            String choice = input.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.print("  Enter New Username: ");
                    String newUsername = input.nextLine().trim();
                    if (!newUsername.isEmpty()) {
                        setUsername(newUsername);
                        System.out.println(" Username updated successfully!");
                    }
                }
                case "2" -> {
                    System.out.print("  Enter New Password: ");
                    String newPassword = input.nextLine().trim();
                    if (!newPassword.isEmpty()) {
                        setPassword(newPassword);
                        System.out.println(" Password updated successfully!");
                    }
                }
                case "3" -> backToMenu = true;
                default -> System.out.println("\n Error: Invalid choice.\n");
            }
        }
    }
}