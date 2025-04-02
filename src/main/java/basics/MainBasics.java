package basics;

import java.util.Scanner;

public class MainBasics {
    private static String[] names = new String[5];
    private static String[] phoneNumbers = new String[5];
    private static Scanner scanner = new Scanner(System.in);
    private static int currentMassiveSize = 0;

    public static void main(String[] args) {
        boolean quit = true;
        while (quit) {
            System.out.println("Выберите опцию из меню: ");
            System.out.println("1. Добавить контакт");
            System.out.println("2. Просмотреть контакты");
            System.out.println("3. Найти контакт");
            System.out.println("4. Удалить контакт");
            System.out.println("5. Выйти");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> addContact();
                case "2" -> getAllContact();
                case "3" -> getContact();
                case "4" -> deleteContact();
                case "5" -> quit = false;
                default -> System.out.println("Выбран не известный пункт меню");
            }
        }
        System.out.println("Программа завершена");
    }

    private static void deleteContact() {
        System.out.println("Введмте имя контакта для удаления: ");
        String nameForDeleting = scanner.nextLine();
        int index = -1;

        for (int i = 0; i < currentMassiveSize; i++) {
            if (names[i].equalsIgnoreCase(nameForDeleting)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Контакт " + nameForDeleting + " не найден");
            return;
        }

        for (int i = index; i < currentMassiveSize - 1; i++) {
            names[i] = names[i + 1];
            phoneNumbers[i] = phoneNumbers[i + 1];
        }

        names[currentMassiveSize - 1] = null;
        phoneNumbers[currentMassiveSize - 1] = null;
        currentMassiveSize--;
        System.out.println("Контакт " + nameForDeleting + " удален");
    }


    private static void addContact() {
        if (currentMassiveSize >= phoneNumbers.length) {
            System.out.println("Место в справичнике закончилось, удалите другой контакт");
            return;
        }
        System.out.print("Введите имя пользователя: ");
        String name = scanner.nextLine();
        System.out.print("Введите номер пользователя: ");
        String phone = scanner.nextLine();

        if (name.isBlank() || phone.isBlank()) {
            System.out.println("Данные не могут быть пустыми");
            return;
        }
        for (String curName : names) {
            if (name.equalsIgnoreCase(curName)) {
                System.out.println("Такой контакт уже есть в справочнике");
                return;
            }
        }
        names[currentMassiveSize] = name;
        phoneNumbers[currentMassiveSize] = phone;
        currentMassiveSize++;
        System.out.println("Контакт добавлен: Имя - " + name + ", номер " + phone);
    }

    private static void getAllContact() {
        if (currentMassiveSize == 0) {
            System.out.println("Справочник еще не заполнен");
            return;
        }
        System.out.println("Вывод всех контактов: ");
        for (int i = 0; i < currentMassiveSize; i++) {
            System.out.println("* Имя: " + names[i] + " номер: " + phoneNumbers[i]);
        }
    }

    private static void getContact() {
        System.out.println("Введите имя для поиска контакта: ");
        String name = scanner.nextLine();
        boolean isFoundContact = false;

        for (int i = 0; i < currentMassiveSize; i++) {
            if (names[i].equalsIgnoreCase(name)) {
                System.out.println("Найден контакт: " + names[i] + ", номер: " + phoneNumbers[i]);
                isFoundContact = true;
            }
        }
        if (!isFoundContact) {
            System.out.println("Контакт " + name + " не найден");
        }
    }
}

