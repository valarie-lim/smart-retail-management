/**
 * Represents bundle deal in smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from Item class.</p>
 */
public class BundleDeal extends Item{
    private int bundleDiscount;
    private String includedItems;

    /**
     * Constructs a BundleDeal object.
     *
     * @param itemCode The item code inherited from Item class.
     * @param itemName The item name inherited from Item class.
     * @param price The item price inherited from Item class.
     * @param bundleDiscount The discount percentage of the bundle deal.
     * @param includedItems The list of items included in the bundle deal.
     */
    public BundleDeal (String itemCode, String itemName, double price, int bundleDiscount, String includedItems){
        super(itemCode, itemName, price);
        this.bundleDiscount = bundleDiscount;
        this.includedItems = includedItems;
    }

    /**
     * Returns the items included in this bundle deal.
     *
     * @return The description of the included items.
     */
    public String getIncludedItems() {
        return includedItems;
    }

    /**
     * Returns the discount percentage of this bundle deal.
     *
     * @return The bundle discount percentage.
     */
    public int getBundleDiscount() {
        return bundleDiscount;
    }

    /**
     * Displays the details of the bundle deal, including items and discount.
     */
    @Override
    public void displayItemDetails() {
        System.out.println(" Included : " + includedItems);
        System.out.printf(" Bundle Discount: %d%%\n", bundleDiscount);
    }
}
