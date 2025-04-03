package oop;

import java.util.Objects;

public class Book extends Publication implements Printable {
    private String ISBN;

    public Book(String title, String author, int year, String isbn) {
        super(title, author, year);
        this.ISBN = isbn;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        if (ISBN.isBlank()) {
            System.out.println("ISBN не может быть пустым");
            return;
        }
        this.ISBN = ISBN;
    }

    @Override
    public String getType() {
        return "Книга";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Book book = (Book) o;
        return Objects.equals(ISBN, book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ISBN);
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return super.toString() + ", ISBN: " + ISBN;
    }
}
