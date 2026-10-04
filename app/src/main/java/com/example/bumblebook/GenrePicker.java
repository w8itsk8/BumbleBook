package com.example.bumblebook;
// created by Kate Wheeler 4 Oct 2026
// BumbleBook Book Inventory Application
// GenrePicker creates checklist of genres

// Import libraries
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.widget.CheckBox;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Instantiate Class
public class GenrePicker extends DialogFragment {

    public interface OnGenresSelectedListener {
        void onGenresSelected(List<String> selectedGenres);
    }

    private static final int INDENT_PER_LEVEL_DP = 24;
    private final Map<Genre, CheckBox> checkBoxViews = new HashMap<>();

    private static final String ARG_CURRENT_GENRES = "current_genres";

    private OnGenresSelectedListener listener;
    private GenreSelection selection;
    private LinearLayout genreListContainer;

    // Constructors
    public static GenrePicker newInstance(List<String> currentGenres) {

        GenrePicker fragment = new GenrePicker();

        Bundle args = new Bundle();
        args.putStringArrayList(ARG_CURRENT_GENRES, new ArrayList<>(currentGenres));
        fragment.setArguments(args);

        return fragment;

    }

    // Called by AddEditActivity before showing this dialog
    public void setOnGenresSelectedListener(OnGenresSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_genre_picker, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        selection = new GenreSelection();

        // Pre-Populate with books current genres
        List<String> currentGenreNames = new ArrayList<>();

        // If there were genres pre-selected
        if (getArguments() != null) {

            // create new list of genres
            List<String> stored = getArguments().getStringArrayList(ARG_CURRENT_GENRES);

            // if newly created list isn't empty
            if (stored != null) {

                // store it
                currentGenreNames = stored;

            }

        }

        // for genre in newly created list
        for (String name : currentGenreNames) {

            // assign genre to temp object
            Genre genre = GenreTree.findByName(name);

            // if genre isn't null
            if (genre != null) {

                // check if it's been selected
                selection.check(genre);

            } else {

                // Otherwise log the error
                Log.e("GenrePicker", "No matching genre found for saved name " + name);

            }

        }

        genreListContainer = view.findViewById(R.id.genreListContainer);

        Button doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(v -> {

            if (listener != null) {
                listener.onGenresSelected(selection.getSelectedNames());
            }

            dismiss();

        });

        for (Genre root : GenreTree.ROOTS) {
            addGenreNode(genreListContainer, root, 0);
        }


    }

    // Recursive build of genre row
    // If children exist, create a collapsible container for each child
    private void addGenreNode(LinearLayout parentContainer, Genre genre, int level) {

        // Wrapper for current node's row and its collapsible children
        LinearLayout nodeContainer = new LinearLayout(requireContext());
        nodeContainer.setOrientation(LinearLayout.VERTICAL);

        // Create full row: checkbox, collapse arrow, label
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        int indentPx = (int) (level * INDENT_PER_LEVEL_DP * getResources().getDisplayMetrics().density);
        row.setPadding(indentPx, 12, 0, 12);

        // Checkbox for selectable genres
        if (genre.isSelectable()) {

            CheckBox checkBox = new CheckBox(requireContext());
            checkBox.setChecked(selection.isChecked(genre));

            // Listen for checks
            checkBox.setOnClickListener(v -> {

                // If box is checked
                if (checkBox.isChecked()) {
                    selection.check(genre);
                    // Otherwise...
                } else {
                    selection.uncheck(genre);
                }

                // Sync other checkboxes
                refreshAllCheckboxes();

            });

            row.addView(checkBox);
            checkBoxViews.put(genre, checkBox);

        }

        // Collapse/Expand Arrow for genres with children
        LinearLayout childrenContainer = null;

        // if genre has children
        if (!genre.getChildren().isEmpty()) {

            // create a container for the children
            childrenContainer = new LinearLayout(requireContext());
            childrenContainer.setOrientation(LinearLayout.VERTICAL);
            childrenContainer.setVisibility(View.GONE);

            // Collapse/Expand Arrow
            TextView arrow = new TextView(requireContext());
            arrow.setText("\u25B6");
            arrow.setPadding(8, 0, 8, 0);

            LinearLayout finalChildrenContainer = childrenContainer;
            arrow.setOnClickListener(v -> {

                boolean isCollapsed = finalChildrenContainer.getVisibility() == View.GONE;
                finalChildrenContainer.setVisibility(isCollapsed ? View.VISIBLE : View.GONE);
                arrow.setText(isCollapsed ? "\u25BC" : "\u25B6");

            });

            row.addView(arrow);

        }

        // Genre name label
        TextView label = new TextView(requireContext());
        label.setText(genre.getName());
        row.addView(label);

        nodeContainer.addView(row);

        // Recursively build children
        if (childrenContainer != null) {

            // for each child of the current genre
            for (Genre child : genre.getChildren()) {

                // add the child's node
                addGenreNode(childrenContainer, child, level + 1);

            }

            nodeContainer.addView(childrenContainer);

        }

        parentContainer.addView(nodeContainer);

    }

    // Syncs checkboxes
    private void refreshAllCheckboxes() {

        for (Map.Entry<Genre, CheckBox> entry : checkBoxViews.entrySet()) {

            entry.getValue().setChecked(selection.isChecked(entry.getKey()));

        }

    }

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

}