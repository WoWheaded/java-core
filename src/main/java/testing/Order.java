package testing;

public class Order {
    private int id;
    private String productName;
    private int quantity;
    private double unitPrice;

    public Order(int id, String productName, int quantity, double unitPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getId() {
        return id;
    }

    public double getTotalPrice() {
        if (quantity < 0 || unitPrice < 0) {
            return 0.0;
        }
        return quantity * unitPrice;
    }
}
