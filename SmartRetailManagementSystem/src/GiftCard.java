/**
 * Represents gift card in smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from Item class.</p>
 */
public class GiftCard extends Item{
    private final double denomination;
    private final String expiryDate;

    /**
     * Constructs a GiftCard object.
     *
     * @param itemCode The unique code of the gift card.
     * @param itemName The name of the gift card.
     * @param price The selling price of the gift card.
     * @param denomination The discount value that the gift card can apply.
     * @param expiryDate The expiry date of the gift card.
     */
    public GiftCard(String itemCode, String itemName, double price, double denomination, String expiryDate){
        super(itemCode, itemName, price);
        this.denomination = denomination;
        this.expiryDate = expiryDate;
    }

    /**
     * Returns the redeemable value of the gift card.
     *
     * @return The gift card denomination.
     */
    public double getDenomination(){
        return this.denomination;
    }

    /**
     * Returns the expiry date of the gift card.
     *
     * @return The gift card expiry date.
     */
    public String getExpiryDate(){
        return this.expiryDate;
    }

    /**
     * Displays the specific details of the gift card, including denomination and expiry date.
     */
    @Override
    public void displayItemDetails() {
        System.out.printf(" Denomination: %.2f\n", denomination);
        System.out.println(" Expiry Date : " + expiryDate);
    }
}
