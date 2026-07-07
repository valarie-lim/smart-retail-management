/**
 * Represents digital payment transaction in smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from Payment class.</p>
 */
public class DigitalPayment extends Payment {
    private final String provider;

    /**
     * Constructs a DigitalPayment object.
     *
     * @param transactionId The unique transaction ID.
     * @param totalAmount The total amount.
     * @param custId The customer ID linked to this payment.
     * @param provider The digital payment provider.
     */
    public DigitalPayment(String transactionId, double totalAmount, String custId, String provider) {
        super(transactionId, totalAmount, custId,"DIGITAL");
        this.provider = provider;
    }

    /**
     * Displays the digital payment provider.
     */
    @Override
    public void displayPaymentDetails() {
        System.out.println(" Paid with " + provider);
    }

    /**
     * Displays a short version details in the transaction history table.
     */
    @Override
    public void displayInTransactionHistory() {
        System.out.printf("%-25s",
                String.format("Paid with %s",provider));
    }
}
