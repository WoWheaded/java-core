package oop;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Publication> publications = new ArrayList<>();

    public void addPublication(Publication pub) {
        if (publications.contains(pub)) {
            System.out.println("Такое произведение " + pub + " уже есть в библиотеке");
            Publication.setPublicationCount();
            return;
        }
        publications.add(pub);
    }

    public void listPublications() {
        if (publications.isEmpty()) {
            System.out.println("В библиотеке нет публикаций.");
            return;
        }
        publications.forEach(System.out::println);
    }

    public void searchByAuthor(String author) {
        long count = publications.stream()
                .filter(el -> el.getAuthor().equalsIgnoreCase(author))
                .peek(System.out::println)
                .count();
        if (count == 0) {
            System.out.println("Публикации автора " + author + " не найдены");
        }
    }
}
