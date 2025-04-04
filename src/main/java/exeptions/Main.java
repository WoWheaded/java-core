package exeptions;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        while (true) {
            printMenu();
            int choice = readIntInput("Выберите пункт меню: ");

            try {
                switch (choice) {
                    case 1 -> showCatalog();
                    case 2 -> addBook();
                    case 3 -> takeBook();
                    case 4 -> returnBook();
                    case 5 -> {
                        System.out.println("Выход из программы");
                        return;
                    }
                    default -> System.out.println("Неверный выбор!");
                }
            } catch (ItemNotFoundException | NoAvailableCopiesException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Показать каталог");
        System.out.println("2. Добавить книгу");
        System.out.println("3. Взять книгу");
        System.out.println("4. Вернуть книгу");
        System.out.println("5. Выйти");
    }

    private static void showCatalog() {
        List<Book> books = library.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("Каталог пуст");
            return;
        }
        books.forEach(System.out::println);
    }

    private static void addBook() {
        String title = readNonEmptyString("Введите название книги: ");
        String author = readNonEmptyString("Введите автора: ");

        int copies = readPositiveInt("Введите количество копий: ");
        library.addBook(title, author, copies);
        System.out.println("Книга " + title + ", автор " + author + " в количестве " + copies + " ед. успешно добавлена");
    }

    private static void takeBook() {
        String title = readNonEmptyString("Введите название книги для выдачи: ");
        library.takeBook(title);
        System.out.println("Книга " + title + " успешно выдана");
    }

    private static void returnBook() {
        String title = readNonEmptyString("Введите название книги для возврата: ");
        library.returnBook(title);
        System.out.println("Книга " + title + " успешно возвращена");
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Введите целое число");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readIntInput(prompt);
            if (value > 0) return value;
            System.out.println("Число должно быть больше нуля");
        }
    }

    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) return input;
            System.out.println("Поле не может быть пустым");
        }
    }
}
