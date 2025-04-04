package oop;

import java.util.Objects;

public class Magazine extends Publication implements Printable {
    private String issueNumber;

    public Magazine(String title, String author, int year, String issueNumber) {
        super(title, author, year);
        this.issueNumber = issueNumber;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        if (issueNumber.isBlank()) {
            System.out.println("Номер выпуска не может быть пустым");
            return;
        }
        this.issueNumber = issueNumber;
    }

    @Override
    public String getType() {
        return "Журнал";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Magazine magazine = (Magazine) o;
        return Objects.equals(issueNumber, magazine.issueNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), issueNumber);
    }

    @Override
    public void printDetails() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        return super.toString() + ", Номер выпуска: " + issueNumber;
    }
}
