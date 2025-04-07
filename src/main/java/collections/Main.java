package collections;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static ContactBook contactBook = new ContactBook();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            switch (scanner.nextLine()) {
                case "1" -> addContactMenu();
                case "2" -> removeContactMenu();
                case "3" -> contactBook.displayContacts();
                case "4" -> searchContactMenu();
                case "5" -> displayByGroupMenu();
                case "0" -> running = false;
                default -> System.out.println("Выбран не известный пункт меню");
            }
        }
        System.out.println("Выход из программы.");
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Добавить контакт");
        System.out.println("2. Удалить контакт");
        System.out.println("3. Посмотреть все контакты");
        System.out.println("4. Найти контакт");
        System.out.println("5. Просмотреть контакты по группе");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private static void addContactMenu() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine().trim();
        System.out.print("Введите телефон: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Введите email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Введите группу: ");
        String group = scanner.nextLine().trim();

        if (name.isEmpty() || phone.isEmpty()) {
            System.out.println("Ошибка: Имя и телефон обязательны");
            return;
        }

        Contact contact = new Contact(name, phone, email, group);
        contactBook.addContact(contact);
    }

    private static void removeContactMenu() {
        System.out.print("Введите имя контакта для удаления: ");
        String name = scanner.nextLine().trim();
        System.out.print("Введите телефон контакта: ");
        String phone = scanner.nextLine().trim();

        Contact contact = contactBook.findContact(name, phone);
        if (contact != null) {
            contactBook.removeContact(contact);
        } else {
            System.out.println("Контакт c именем " + name + " не найден.");
        }
    }

    private static void searchContactMenu() {
        System.out.print("Введите имя для поиска: ");
        String name = scanner.nextLine().trim();
        contactBook.searchContacts(name);
    }

    private static void displayByGroupMenu() {
        System.out.print("Введите название группы: ");
        String group = scanner.nextLine().trim();
        contactBook.displayContactsByGroup(group);
    }
}
