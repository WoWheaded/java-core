package oop;

import java.util.Objects;

public abstract class Publication {
    private String title;
    private String author;
    private int year;
    private static int publicationCount =  0;

    public abstract String getType();

    public Publication(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
        publicationCount++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isBlank()) {
            System.out.println("название не может быть пустым");
            return;
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if (author.isBlank()) {
            System.out.println("автор не может быть пустым");
            return;
        }
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 1700 ) {
            System.out.println("Публикация не может быть такой старой");
            return;
        }
        this.year = year;
    }

    public static int getPublicationCount() {
        return publicationCount;
    }

    public static void setPublicationCount() {
        publicationCount--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return year == that.year && Objects.equals(title, that.title) && Objects.equals(author, that.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

    @Override
    public String toString() {
        return String.format("[%s] Название: %s, Автор: %s, Год: %d", this.getType(), title, author, year);
    }
}
