package testing;

public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public String processOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order не может быть null");
        }
        int i = orderRepository.saveOrder(order);
        if (i != 0) {
            return "Order processed successfully";
        } else {
            return "Order processing failed";
        }
    }

    public double calculateTotal(int id) {
        return orderRepository.getOrderById(id)
                .stream()
                .mapToDouble(Order::getTotalPrice)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Order не найден"));
    }
}
