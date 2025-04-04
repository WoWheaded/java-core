package collections;

import java.util.*;

public class ContactBook {
    private List<Contact> contactList = new LinkedList<>();
    private Set<Contact> contactSet  = new HashSet<>();
    private Map<String, List<Contact>> contactMap = new HashMap<>();

    public void addContact(Contact contact) {
        if (contactSet.add(contact)) {
            contactList.add(contact);
            String group = contact.getGroup().isEmpty() ? "Без группы" : contact.getGroup();
            contactMap.computeIfAbsent(group, k -> new ArrayList<>()).add(contact);
            System.out.println("Контакт " + contact + " добавлен");
        } else {
            System.out.println("Контакт " + contact + " уже существует");
        }
    }

    public void removeContact(Contact contact) {
        if (contactSet.remove(contact)) {
            contactList.remove(contact);
            String group = contact.getGroup();
            List<Contact> groupContacts = contactMap.get(group);
            if (groupContacts != null) {
                groupContacts.remove(contact);
                if (groupContacts.isEmpty()) {
                    contactMap.remove(group);
                }
            }
            System.out.println("Контакт " + contact + " удален");
        } else {
            System.out.println("Контакт " + contact + " не найден");
        }
    }

    public void displayContacts() {
        if (contactList.isEmpty()) {
            System.out.println("Список контактов пуст");
            return;
        }
        Iterator<Contact> iterator = contactList.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public void searchContacts(String name) {
        boolean found = false;
        Iterator<Contact> iterator = contactList.iterator();
        while (iterator.hasNext()) {
            Contact contact = iterator.next();
            if (contact.getName().equalsIgnoreCase(name)) {
                System.out.println(contact);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Контакты с именем '" + name + "' не найдены");
        }
    }

    public void displayContactsByGroup(String group) {
        List<Contact> groupContacts = contactMap.getOrDefault(group, new ArrayList<>());
        if (groupContacts.isEmpty()) {
            System.out.println("Группа '" + group + "' не найдена или пуста");
        } else {
            Iterator<Contact> iterator = groupContacts.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }
        }
    }

    public Contact findContact(String name, String phone) {
        Contact temp = new Contact(name, phone);
        if (contactSet.contains(temp)) {
            for (Contact contact : contactSet) {
                if (contact.equals(temp)) {
                    return contact;
                }
            }
        }
        return null;
    }
}