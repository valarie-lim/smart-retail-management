/**
 * Represents cash payment transaction in smart retail system.
 * <p>A subclass that inherits common attributes and behaviours from Payment class.</p>
 */
public class CashPayment extends Payment{
    private final double cashReceived;
    private final double changeDue;

    /**
     * Constructs a CashPayment object.
     *
     * @param transactionId The unique transaction ID.
     * @param totalAmount The total amount.
     * @param custId The customer ID linked to this payment.
     * @param cashReceived THe cash amount received from the customer.
     */
    public CashPayment(String transactionId, double totalAmount, String custId, double cashReceived) {
        super(transactionId, totalAmount, custId, "CASH");
        this.cashReceived = cashReceived;
        this.changeDue = cashReceived - totalAmount;
    }

    /**
     * Displays the cash payment details, including cash received and change due.
     */
    @Override
    public void displayPaymentDetails() {
        System.out.printf(" %-20s  %10.2f\n", "Cash Received", cashReceived);
        System.out.printf(" %-20s  %10.2f\n", "Change Due", changeDue);
    }

    /**
     * Displays a short version details in the transaction history table.
     */
    @Override
    public void displayInTransactionHistory() {
        System.out.printf("%-25s",
                String.format("Recv(%.2f),Chg(%.2f)", cashReceived, changeDue));
    }

}
