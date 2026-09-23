package com.example.bumblebook;
// created by Kate Wheeler 20 Sept 2026
// BumbleBook Book Inventory Application
// Book defines book object

// initiate class
public class Book {

    // define class-level variables
    private int id;
    private String title1;
    private String title2;
    private String title3;
    private String edition;
    private int printYear;
    private String publisher;
    private String author;
    private String editor;
    private String genre;
    private int quantity;

    // Constructor
    public Book(int id, String title1, String title2, String title3, String edition,
                int printYear, String publisher, String author, String editor,
                String genre, int quantity) {

        this.id = id;
        this.title1 = title1;
        this.title2 = title2;
        this.title3 = title3;
        this.edition = edition;
        this.printYear = printYear;
        this.publisher = publisher;
        this.author = author;
        this.editor = editor;
        this.genre = genre;
        this.quantity = quantity;

    }

    // ID Getter
    public int getID() {

        // Return ID of specified book
        return id;
    }

    // Main title getter
    public String getTitle1() {

        // Return main title of specified book
        return title1;

    }

    // Subtitle getter
    public String getTitle2() {

        // Return subtitle of specified book
        return title2;

    }

    // Tertiary title getter
    public String getTitle3() {

        // Return tertiary title of specified book
        return title3;

    }

    // Edition title getter
    public String getEdition() {

        // Return edition title of specified book
        return edition;

    }

    // Print year getter
    public int getPrintYear() {

        // Return year specified book was printed
        return printYear;

    }

    // Publisher getter
    public String getPublisher() {

        // Return publisher of specified book
        return publisher;

    }

    // Author getter
    public String getAuthor() {

        // Return author of specified book
        return author;

    }

    // Editor getter
    public String getEditor() {

        // Return editor of specified book
        return editor;

    }

    // Genre getter
    public String getGenre() {

        // Return genre(s) of specified book
        return genre;

    }

    // Quantity getter
    public int getQuantity() {

        // Return quantity of specified book
        return quantity;

    }


}
