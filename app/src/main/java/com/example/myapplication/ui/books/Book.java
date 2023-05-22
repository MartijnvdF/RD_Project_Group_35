package com.example.myapplication.ui.books;

public class Book {
    private String isbn, author, title, version, year, course, link;

    public Book(String isbn, String author, String title, String version, String year, String course, String link) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.version = version;
        this.year = year;
        this.course = course;
        this.link = link;
    }

    public String get_isbn(){return isbn;}

    public String get_author(){return author;}

    public String get_title(){return title;}

    public String get_version(){return version;}

    public String get_year(){return year;}

    public String get_course(){return course;}

    public String get_link(){return link;}
}
