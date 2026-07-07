/**
 * Transaction related behaviours in this System.
 * Class that implements this interface must provide its own transaction process,
 * discount and receipt generate
 */
public interface Transactable {
    boolean processTransaction();
    double applyDiscount(double subtotal, double discountAmount);
    void generateReceipt(Payment payment, double subtotal, double discount, double roundingAdj, double roundedTotal);
}