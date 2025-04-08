package streams;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product(9L, "Молочник", "Children's products", new BigDecimal("25.99"));
        Product p2 = new Product(10L, "Памперсы", "Children's products", new BigDecimal("89.95"));
        Product p3 = new Product(15L, "Java Core", "Books", new BigDecimal("59.99"));
        Product p4 = new Product(16L, "Design Patterns", "Books", new BigDecimal("89.99"));
        Product p5 = new Product(17L, "Clean Code", "Books", new BigDecimal("1000.00"));
        Product p6 = new Product(32L, "Lego", "Toys", new BigDecimal("79.99"));
        Product p7 = new Product(33L, "CD", "Toys", new BigDecimal("45.00"));
        Product p8 = new Product(34L, "Puzzle", "Toys", new BigDecimal("29.95"));
        Product p9 = new Product(35L, "Solders", "Toys", new BigDecimal("19.99"));
        Product p10 = new Product(36L, "Spider-man", "Toys", new BigDecimal("24.99"));
        Product p11 = new Product(37L, "Ball", "Toys", new BigDecimal("129.00"));

        Order o1 = new Order(1L, LocalDate.of(2021, 2, 1), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p1, p2)));
        Order o2 = new Order(2L, LocalDate.now(), LocalDate.now().minusDays(1), "closed", new HashSet<>(Arrays.asList(p3, p4)));
        Order o3 = new Order(3L, LocalDate.of(2021, 2, 3), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p5, p6)));
        Order o4 = new Order(4L, LocalDate.of(2021, 3, 15), LocalDate.now().plusDays(1), "close", new HashSet<>(Arrays.asList(p11, p9)));
        Order o5 = new Order(5L, LocalDate.now().minusDays(2), LocalDate.now().plusDays(1), "closed", new HashSet<>(Arrays.asList(p1, p7)));
        Order o6 = new Order(6L, LocalDate.now().minusDays(3), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p8, p2)));
        Order o7 = new Order(7L, LocalDate.now().minusDays(4), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p10, p7)));
        Order o8 = new Order(8L, LocalDate.now().minusDays(5), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p2, p8)));
        Order o9 = new Order(9L, LocalDate.now().minusDays(6), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p5, p9)));
        Order o10 = new Order(10L, LocalDate.of(2021, 3, 14), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p6, p11)));
        Order o11 = new Order(11L, LocalDate.of(2021, 3, 14), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p4, p3)));
        Order o12 = new Order(12L, LocalDate.now().minusDays(9), LocalDate.now().plusDays(1), "opened", new HashSet<>(Arrays.asList(p2, p8)));


        List<Customer> customers = new ArrayList<>(Arrays.asList(
                new Customer(1L, "Ivan", 1L, new HashSet<>(Arrays.asList(o1, o4, o5, o7, o2))),
                new Customer(2L, "Alex", 2L, new HashSet<>(Arrays.asList(o2, o6, o11, o3, o11))),
                new Customer(3L, "Olga", 3L, new HashSet<>(Arrays.asList(o3, o4, o1, o7, o12))),
                new Customer(4L, "Mark", 4L, new HashSet<>(Arrays.asList(o4, o8, o10, o6, o9))),
                new Customer(5L, "Inessa", 5L, new HashSet<>(Arrays.asList(o11, o4, o5, o9, o5)))
        ));

        // 1) Получите список продуктов из категории "Книги" с ценой более 100.
        Set<Product> bookProduct = customers.stream()
                .flatMap(or -> or.getOrders().stream())
                .flatMap(pr -> pr.getProducts().stream())
                .filter(el -> el.getCategory().equalsIgnoreCase("Books") && el.getPrice().compareTo(new BigDecimal(100)) > 0)
                .collect(Collectors.toSet());

        // 2) Получите список заказов с продуктами из категории "Children's products".
        List<Order> orders1 = customers.stream()
                .flatMap(or -> or.getOrders().stream())
                .filter(el -> el.getProducts().stream()
                        .anyMatch(pr -> "Children's products".equalsIgnoreCase(pr.getCategory())))
                .toList();

        //3. Получите список продуктов из категории "Toys" и примените скидку 10% и получите сумму всех
        //продуктов.
        BigDecimal sumToys = customers.stream()
                .flatMap(cs -> cs.getOrders().stream())
                .flatMap(or -> or.getProducts().stream())
                .filter(pr -> "Toys".equalsIgnoreCase(pr.getCategory()))
                .map(el -> el.getPrice().multiply(BigDecimal.valueOf(0.9)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        //4. Получите список продуктов, заказанных клиентом второго уровня между 01-фев-2021 и 01-апр-2021.
        List<Product> prod1 = customers.stream()
                .filter(cus -> cus.getLevel() > 2L)
                .flatMap(customer -> customer.getOrders().stream())
                .filter(or -> or.getOrderDate().isAfter(LocalDate.of(2021, 2, 1)) &&
                        or.getOrderDate().isBefore(LocalDate.of(2021, 4, 1)))
                .flatMap(prod -> prod.getProducts().stream())
                .toList();

        //5. Получите топ 2 самые дешевые продукты из категории "Books"
        List<Product> top2CheapestBook = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .flatMap(order -> order.getProducts().stream())
                .filter(book -> "Books".equalsIgnoreCase(book.getCategory()))
                .distinct()
                .sorted(Comparator.comparing(Product::getPrice))
                .limit(2)
                .toList();

        //6. Получите 3 самых последних сделанных заказа.
        List<Order> latest3Orders = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .distinct()
                .limit(3)
                .toList();

        //7. Получите список заказов, сделанных 15-марта-2021, выведите id заказов в консоль и затем верните
        //список их продуктов
        List<Product> filteredProduct = customers.stream()
                .flatMap(customer -> customer.getOrders().stream())
                .filter(date -> date.getOrderDate().isEqual(LocalDate.of(2021, 3, 15)))
                .distinct()
                .peek(orderId -> System.out.println(orderId.getId()))
                .flatMap(prod -> prod.getProducts().stream())
                .toList();

        //8. Рассчитайте общую сумму всех заказов, сделанных в феврале 2021.
        BigDecimal sumOrders = customers.stream()
                .flatMap(orders -> orders.getOrders().stream())
                .filter(or -> LocalDate.of(2021, 2, 1).isAfter(or.getOrderDate()) &&
                        LocalDate.of(2021, 2, 28).isBefore(or.getOrderDate()))
                .flatMap(order -> order.getProducts().stream())
                .map(Product::getPrice)
                .reduce(BigDecimal.valueOf(0), BigDecimal::add);

        //9. Рассчитайте средний платеж по заказам, сделанным 14-марта-2021
        var average = customers.stream()
                .flatMap(cus -> cus.getOrders().stream())
                .filter(order -> LocalDate.of(2021, 3, 14).equals(order.getOrderDate()))
                .flatMap(pr -> pr.getProducts().stream())
                .mapToDouble(p -> p.getPrice().doubleValue())
                .average();

        //10.Получите набор статистических данных (сумма, среднее, максимум, минимум, количество) для всех
        //продуктов категории "Книги".
        DoubleSummaryStatistics statistics = customers.stream()
                .flatMap(c -> c.getOrders().stream())
                .flatMap(o -> o.getProducts().stream())
                .filter(p -> "Books".equalsIgnoreCase(p.getCategory()))
                .mapToDouble(p -> p.getPrice().doubleValue())
                .summaryStatistics();
        double sum = statistics.getSum();
        double averages = statistics.getAverage();
        double max = statistics.getMax();
        double min = statistics.getMin();
        double count = statistics.getCount();

        // 11 Получите данные Map<Long, Integer> → key - id заказа, value - кол-во товаров в заказе
        Map<Long, Integer> map1 = customers.stream()
                .flatMap(cus -> cus.getOrders().stream())
                .distinct()
                .collect(Collectors.toMap(
                        Order::getId,
                        order -> order.getProducts().size()
                ));

        //12. Создайте Map<Customer, List<Order>> → key - покупатель, value - список его заказов
        Map<Customer, List<Order>> map2 = customers.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        list -> list.getOrders().stream().toList()));

        //13. Создайте Map<Order, Double> → key - заказ, value - общая сумма продуктов заказа.
        Map<Order, Double> map3 = customers.stream()
                .flatMap(cus -> cus.getOrders().stream())
                .distinct()
                .collect(Collectors.toMap(
                        order -> order,
                        prod -> prod.getProducts().stream()
                                .mapToDouble(el -> el.getPrice().doubleValue())
                                .sum()
                ));

        //14. Получите Map<String, List<String>> → key - категория, value - список названий товаров в категории
        Map<String, List<String>> map4 = customers.stream()
                .flatMap(cus -> cus.getOrders().stream())
                .flatMap(or -> or.getProducts().stream())
                .distinct()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.mapping(Product::getName, Collectors.toList())));

        //15. Получите Map<String, Product> → самый дорогой продукт по каждой категории.
        Map<String, Product> map5 = customers.stream()
                .flatMap(cus -> cus.getOrders().stream())
                .flatMap(or -> or.getProducts().stream())
                .collect(Collectors.toMap(
                        Product::getCategory,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Product::getPrice))));
        System.out.println(map5);
    }
}

