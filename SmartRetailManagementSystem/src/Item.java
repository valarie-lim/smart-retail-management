/**
 * Abstract superclass for the smart retail's item (product, gift card, bundle deal).
 * <p>This is a base class that provides common attributes including
 * item name, item code and price to its subclasses.</p>
 */
public abstract class Item {
    private final String itemCode;
    private String itemName;
    private double price;

    /**
     * Constructs an Item object.
     *
     * @param itemCode The unique code of the item.
     * @param itemName The name of the item.
     * @param price The price of the item.
     */
    public Item(String itemCode, String itemName, double price){
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.price = price;
    }

    /**
     * Returns the unique item code.
     *
     * @return The item code.
     */
    public String getItemCode() {
        return this.itemCode;
    }

    /**
     * Returns the item name.
     *
     * @return The item name.
     */
    public String getItemName(){
        return this.itemName;
    }

    /**
     * Returns the item price.
     *
     * @return The item price.
     */
    public double getPrice(){
        return this.price;
    }

    /**
     * Updates the item name if the new name is not empty.
     *
     * @param itemName The new item name.
     */
    public void setItemName(String itemName){
        if (itemName != null && !itemName.trim().isEmpty()) {
            this.itemName = itemName;
        }
    }

    /**
     * Updates the item price if the price is greater than zero.
     *
     * @param price The new item price.
     */
    public void setPrice(double price){
        if (price <= 0) {
            System.out.println(" Error: Price must be greater than RM 0.");
        } else {
            this.price = price;
        }
    }

    /**
     * Displays the specific details of an item.
     * <p>Each subclass must provide its own implementation.</p>
     */
    public abstract void displayItemDetails();
}
