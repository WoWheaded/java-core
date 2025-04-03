package exeptions;

import java.util.ArrayList;
import java.util.List;

public class Library {

    private List<Book> catalog = new ArrayList<>();

    public void addBook(String title, String author, int copies) {
        Book addedBook = new Book(title, author, copies);
        if (catalog.contains(addedBook)) {
            System.out.println("Такая книга с таким автором уже есть в библиотеке, добавлю к существующей");
            Book oldBook = findBook(title);
            oldBook.setAvailableCopies(oldBook.getAvailableCopies() + copies);
            return;
        }
        catalog.add(addedBook);
    }

    public void takeBook(String title) {
        Book book = findBook(title);
        if (book.getAvailableCopies() <= 0) {
            throw new NoAvailableCopiesException("Нет доступных копий для книги: " + title);
        }
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(String title) {
        Book book = findBook(title);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }

    public List<Book> getAllBooks() {
        return catalog;
    }

    private Book findBook(String title) {
        return catalog.stream()
                .filter(b -> b.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Книга " + title + " не найдена"));
    }
}