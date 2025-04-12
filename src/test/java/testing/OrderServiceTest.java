package testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    private OrderRepository orderRepository;
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
    }

    @Test
    void testProcessOrder_Success() {
        Order order = new Order(1, "Laptop", 2, 1500.0);

        when(orderRepository.saveOrder(order)).thenReturn(1);
        String result = orderService.processOrder(order);

        assertEquals("Order processed successfully", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void testProcessOrder_Failure() {
        Order order = new Order(2, "Phone", 1, 800.0);
        when(orderRepository.saveOrder(order)).thenReturn(0);

        String result = orderService.processOrder(order);

        assertEquals("Order processing failed", result);
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void testProcessOrder_ExceptionHandling() {
        Order order = new Order(3, "Tablet", 1, 600.0);
        when(orderRepository.saveOrder(order)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            orderService.processOrder(order);
        });
        assertEquals("Database error", exception.getMessage());
        verify(orderRepository, times(1)).saveOrder(order);
    }

    @Test
    void testProcessOrder_ExceptionHandling_WhenOrderNull() {
        Order order = null;
        when(orderRepository.saveOrder(order)).thenThrow(new IllegalArgumentException("Order cannot be null"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.processOrder(order);
        });
        assertEquals("Order не может быть null", exception.getMessage());
        verify(orderRepository, times(0)).saveOrder(order);
    }

    @Test
    void testCalculateTotal_Success() {
        Order order = new Order(1, "Laptop", 3, 100.0);
        when(orderRepository.getOrderById(order.getId())).thenReturn(Optional.of(order));

        double result = orderService.calculateTotal(order.getId());

        assertEquals(300.00, result);
        verify(orderRepository, times(1)).getOrderById(order.getId());
    }

    @Test
    void testCalculateTotal_NoOrderFound() {
        Optional<Order> order = Optional.empty();
        when(orderRepository.getOrderById(anyInt())).thenReturn(order);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.calculateTotal(anyInt());
        });
        assertEquals("Order не найден", exception.getMessage());
        verify(orderRepository, times(1)).getOrderById(anyInt());
    }

    @Test
    void testCalculateTotal_ZeroQuantity() {
        Order order = new Order(1, "Laptop", 0, 100.0);
        when(orderRepository.getOrderById(1)).thenReturn(Optional.of(order));

        double result = orderService.calculateTotal(order.getId());

        assertEquals(0.0, result);
        verify(orderRepository, times(1)).getOrderById(1);
    }
}
