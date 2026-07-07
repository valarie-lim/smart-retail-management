import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Abstract superclass for the smart retail's payment (cash and digital).
 * <p>This is a base class that provides common attributes including
 * transaction id, amount, payment date, payment method, and refund flag to its subclasses.</p>
 */
public abstract class Payment {
    private final String transactionId;
    private final double totalAmount;
    private final String paymentDate;
    private final String paymentTime;
    private final String paymentMethod;
    private final String custId;
    private boolean isActive = true;

    /**
     * Constructs a Payment object.
     *
     * @param transactionId The unique transaction ID.
     * @param totalAmount The total payment amount.
     * @param custId The customer ID linked to this payment.
     * @param paymentMethod The payment method used, such as CASH or DIGITAL.
     */
    public Payment(String transactionId, double totalAmount, String custId, String paymentMethod) {
        this.transactionId = transactionId;
        this.totalAmount = totalAmount;
        this.paymentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.paymentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        this.paymentMethod = paymentMethod;
        this.custId = custId;
    }

    /**
     * Returns the transaction ID.
     *
     * @return The transaction ID.
     */
    public String getTransactionId() { return transactionId; }

    /**
     * Returns the total payment amount.
     *
     * @return The total amount.
     */
    public double getTotalAmount() { return totalAmount; }

    /**
     * Returns the payment date.
     *
     * @return The payment date.
     */
    public String getPaymentDate() { return paymentDate; }

    /**
     * Returns the payment time.
     *
     * @return The payment time.
     */
    public String getPaymentTime() { return paymentTime; }

    /**
     * Returns the payment method.
     *
     * @return The payment method.
     */
    public String getPaymentMethod(){ return this.paymentMethod; }

    /**
     * Returns the customer ID linked to the transaction.
     *
     * @return The customer ID.
     */
    public String getCustId(){ return this.custId; }

    /**
     * Returns the current transaction status.
     *
     * @return true if the transaction is active, false if it has been cancelled or refunded.
     */
    public boolean getTransactionStatus(){
        return isActive;
    }

    /**
     * Cancels the transaction by marking it as inactive.
     */
    public void cancelTransaction(){
        this.isActive = false;
    }

    /**
     * Displays payment-specific details.
     * <p>Each payment subclass must provide its own implementation.</p>
     */
    public abstract void displayPaymentDetails();

    /**
     * Displays payment-specific information inside the transaction history table.
     * <p>Each payment subclass must provide its own implementation.</p>
     */
    public abstract void displayInTransactionHistory();

    /**
     * Displays all transaction records in table format.
     *
     * @param transactionHistory The list of payment transactions to display.
     */
    public static void displayTransactionRecord(ArrayList<Payment> transactionHistory){

        System.out.printf("%-13s  %-9s  %-8s  %-12s  %-10s  %-11s  %-25s  %-20s \n",
                "Trans ID", "Method", "Cust ID", "Date", "Time", "Amount(RM)", "Details", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        for (Payment payment : transactionHistory) {
            System.out.printf("%-13s  %-9s  %-8s  %-12s  %-10s  %-11.2f ",
                    payment.getTransactionId(),
                    payment.getPaymentMethod(),
                    payment.getCustId(),
                    payment.getPaymentDate(),
                    payment.getPaymentTime(),
                    payment.getTotalAmount());

            payment.displayInTransactionHistory();

            if (payment.getTransactionStatus()) {
                System.out.println(" Completed");
            } else {
                System.out.println(" Cancelled & Refunded");
            }

            System.out.println("------------------------------------------------------------------------------------------------------------------------");
        }
    }
}