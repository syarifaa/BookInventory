package com.example.syarifaa.bookinventory.activity;

import java.io.Serializable;

/**
 * Created by Syarifaa on 4/17/2017.
 */
public class Book implements Serializable {
    private String ISBN;
    private String book_title;
    private String book_author;
    private String book_genre;
    private String book_synopsis;
    private int published_year;
    private int book_cover;

    //constructor
    public Book(String pISBN, String book_title, String pbook_author, String book_genre, String synopsis, int pPublishedYear, int cover) {
        ISBN = pISBN;
        this.book_title = book_title;
        book_author = pbook_author;
        published_year = pPublishedYear;
        this.book_genre = book_genre;
        book_synopsis = synopsis;
        book_cover = cover;
    }

    //empty constructor
    public Book() {

    }

    //Getter & Setter
    public String getBook_genre() {
        return book_genre;
    }

    public void setBook_genre(String book_genre) {
        this.book_genre = book_genre;
    }

    public int getPublished_year() {
        return published_year;
    }

    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    public String getBook_author() {
        return book_author;
    }

    public void setBook_author(String book_author) {
        this.book_author = book_author;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getBook_cover() {
        return book_cover;
    }

    public void setBook_cover(int book_cover) {
        this.book_cover = book_cover;
    }

    public String getBook_synopsis() {
        return book_synopsis;
    }

    public void setBook_synopsis(String book_synopsis) {
        this.book_synopsis = book_synopsis;
    }
}
