/**
 * Represents product in smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from Item class.</p>
 */
public class Product extends Item{
    private String category;
    private int stockQuantity;

    /**
     * Constructs a Product object.
     * @param itemCode The item code inherited from Item class.
     * @param itemName The item name inherited from Item class.
     * @param price The item price inherited from Item class.
     * @param category The category of the product.
     * @param stockQuantity The stock quantity of the product.
     */
    public Product(String itemCode, String itemName, double price, String category, int stockQuantity){
        super(itemCode, itemName, price);
        this.category = category;
        this.stockQuantity = stockQuantity;
    }

    /**
     * Returns The category of the product.
     * @return The product's category.
     */
    public String getCategory(){
        return this.category;
    }

    /**
     * Returns the stock quantity of the product.
     * @return The product's stock quantity.
     */
    public int getStockQuantity() {
        return stockQuantity;
    }

    /**
     * Sets the category of the product.
     * @param category The category of the product.
     */
    public void setCategory(String category){
        if (category != null && !category.trim().isEmpty()) {
            this.category = category;
        }
    }

    /**
     * Sets the stock quantity of the product.
     * @param stockQuantity The stock quantity of the product.
     */
    public void setStockQuantity(int stockQuantity) {
        if (stockQuantity < 0) {
            System.out.println(" Error: Quantity cannot be less than 0.");
        } else {
            this.stockQuantity = stockQuantity;
        }
    }

    /**
     * Displays the product details, including category and stock quantity.
     */
    @Override
    public void displayItemDetails() {
        System.out.println(" Category : " + category);
        System.out.println(" Stock Qty: " + stockQuantity);
    }
}
