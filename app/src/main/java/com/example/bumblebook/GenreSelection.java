package com.example.bumblebook;



// Import libraries
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

// Instantiate Class
public class GenreSelection {

    private final Set<Genre> checked = new HashSet<>();


    // Check a genre, auto-checking any selectable ancestors that aren't already checked
    public void check(Genre genre) {

        checked.add(genre);

        Genre current = genre.getParent();
        while (current != null) {

            if (current.isSelectable()) {

                // Already checked means everything further up is guaranteed
                // checked too (the invariant this class maintains), so stop
                if (checked.contains(current)) {
                    break;
                }

                checked.add(current);

            }

            current = current.getParent();

        }

    }

    // Uncheck a genre, then uncheck ancestors only if nothing else checked still needs them
    public void uncheck(Genre genre) {

        checked.remove(genre);

        Genre current = genre.getParent();
        while (current != null) {

            if (current.isSelectable()) {

                // If something else checked still lives under this ancestor,
                // it (and everything above it) must stay checked -- stop here
                if (hasCheckedDescendant(current)) {
                    break;
                }

                checked.remove(current);

            }

            current = current.getParent();

        }

    }

    // Recursively check whether any descendant of this genre is currently checked
    private boolean hasCheckedDescendant(Genre genre) {

        for (Genre child : genre.getChildren()) {

            if (checked.contains(child) || hasCheckedDescendant(child)) {
                return true;
            }

        }

        return false;

    }

    public boolean isChecked(Genre genre) {
        return checked.contains(genre);
    }

    public List<String> getSelectedNames() {

        List<String> names = new java.util.ArrayList<>();
        for (Genre genre : checked) {
            names.add(genre.getName());
        }
        return names;
    }

    /*
    @Override
    public void onStart() {

        super.onStart();

        // Force this dialog to fill the entire screen, rather than the
        // default small centered box a DialogFragment normally renders as
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
        }

    }

     */

}









