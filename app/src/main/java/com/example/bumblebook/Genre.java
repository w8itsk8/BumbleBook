package com.example.bumblebook;
// created by Kate Wheeler 3 Oct 2026
// BumbleBook Book Inventory Application
// Genre creates data-structure to hold genre information for each book

// Import libraries
import java.util.ArrayList;
import java.util.List;

// initiate class
public class Genre {

    // define class-level variables
    private final String name;
    private final Genre parent;
    private final boolean selectable;
    private final List<Genre> children = new ArrayList<>();

    // Constructor
    public Genre(String name, Genre parent, boolean selectable) {

        this.name = name;
        this.parent = parent;
        this.selectable = selectable;

        // If this genre is not a subgenre
        if (parent != null) {

            parent.children.add(this);

        }

    }

    // Genre name getter
    public String getName() {

        // Return name of genre
        return name;

    }

    // Genre parent getter
    public Genre getParent() {

        // Return genre parent
        return parent;

    }

    // check if item in genre list is selectable
    public boolean isSelectable() {

        // return status of selectability
        return selectable;

    }

    // genre children getter
    public List<Genre> getChildren() {

        // Return genre children
        return children;

    }

}
