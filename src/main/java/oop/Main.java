package oop;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = new Library();

    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.println("Выберите пункт меню: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addPublication();
                case 2 -> library.listPublications();
                case 3 -> searchPublication();
                case 4 -> System.out.println("Всего публикаций: " + Publication.getPublicationCount());
                case 5 -> isRunning = false;
                default -> System.out.println("Неверный ввод");
            }
        }
        System.out.println("Программа завершена.");
    }

    private static void printMenu() {
        System.out.println("\n1. Добавить новую публикацию.");
        System.out.println("2. Вывести список всех публикаций.");
        System.out.println("3. Поиск публикации по автору.");
        System.out.println("4.  Вывести общее количество публикаций ");
        System.out.println("5. Выйти");
    }

    private static void addPublication() {
        System.out.println("Выберите тип публикации: ");
        System.out.println("1. Книга");
        System.out.println("2. Журнал");
        System.out.println("3. Газета");

        int type;
        while (true) {
            try {
                type = scanner.nextInt();
                scanner.nextLine();
                if (type >= 1 && type <= 3) break;
                System.out.println("Неверный тип, введите 1, 2 или 3.");
            } catch (NumberFormatException e) {
                System.out.println("Ошибка, введите число от 1 до 3.");
            }
        }

        System.out.print("Название: ");
        String title = scanner.nextLine();
        System.out.print("Автор: ");
        String author = scanner.nextLine();
        System.out.print("Год издания: ");
        int year = Integer.parseInt(scanner.nextLine());
        switch (type) {
            case 1 -> {
                System.out.print("ISBN: ");
                String ISBN = scanner.nextLine();
                library.addPublication(new Book(title, author, year, ISBN));
            }
            case 2 -> {
                System.out.print("Номер выпуска: ");
                String issue = scanner.nextLine();
                library.addPublication(new Magazine(title, author, year, issue));
            }
            case 3 -> {
                System.out.print("День публикации: ");
                String day = scanner.nextLine();
                library.addPublication(new Newspaper(title, author, year, day));
            }
        }
    }

    public static void searchPublication() {
        scanner.nextLine();
        System.out.print("Введите имя автора: ");
        String author = scanner.nextLine();
        library.searchByAuthor(author);
    }
}
