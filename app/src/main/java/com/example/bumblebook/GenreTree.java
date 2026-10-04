package com.example.bumblebook;
// created by Kate Wheeler 3 Oct 2026
// BumbleBook Book Inventory Application
// GenreTree holds the genre-sorting data structure

// Import libraries
import java.util.ArrayList;
import java.util.List;

// Initial class
public class GenreTree {

    // "Roots" of tree, top level nodes
    public static final List<Genre> ROOTS = new ArrayList<>();

    // create genres available for assigning
    static {


        // Level 1 Tree Root -- Fiction
        Genre fiction = new Genre("Fiction", null, true);
        ROOTS.add(fiction);

        // Level 2 Child of Fiction -- Poetry
        Genre poetry = new Genre("Poetry", fiction, true);

        // Level 3 Child of Poetry -- Poetry Collection
        Genre poetryCollection = new Genre("Poetry Collection", poetry, true);

        // Level 4 Child of Poetry Collection -- Stanza Poetry
        Genre stanzaPoetry = new Genre("Stanza Poetry", poetryCollection, true);

        // Level 5 Child of Stanza Poetry -- Acrostic
        new Genre("Acrostic", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- Cinquain
        new Genre("Cinquain", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- Haiku
        new Genre("Haiku", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- Limerick
        new Genre("Limerick", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- List
        new Genre("List", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- Ode
        new Genre("Ode", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- Sestina
        new Genre("Sestina", stanzaPoetry, true);

        // Level 5 Child of Stanza Poetry -- Sonnet
        new Genre("Sonnet", stanzaPoetry, true);

        // Level 4 Child of Poetry Collection -- Freeform
        new Genre("Freeform", poetryCollection, true);

        // Level 3 Child of Poetry -- Includes Poetry
        new Genre("Includes Poetry", poetry, true);

        // Level 2 Child of Fiction -- Prose
        Genre prose = new Genre("Prose", fiction, true);

        // Organizational Only Level 3 Child of Prose -- For An Age Group
        Genre forAnAgeGroup = new Genre("For An Age Group", prose, false);

        // Level 4 Child of For An Age Group -- Children's Literature
        Genre childLit = new Genre("Children's Literature", forAnAgeGroup, true);

        // Level 5 Child of Children's Literature -- Fables
        new Genre("Fables", childLit, true);

        // Level 5 Child of Children's Literature -- Fairy Tales
        new Genre("Fairy Tales", childLit, true);

        // Level 5 Child of Children's Literature -- Myths/Folklore/Legends
        new Genre("Myths/Folklore/Legends", childLit, true);

        // Level 5 Child of Children's Literature -- Picture Books
        new Genre("Picture Books", childLit, true);

        // Level 5 Child of Children's Literature -- Rhyming
        new Genre("Rhyming", childLit, true);

        // Level 4 Child of For An Age Group -- Young Adult Fiction
        Genre YAFiction = new Genre("Young Adult Fiction", forAnAgeGroup, true);

        // Level 5 Child of Young Adult Fiction -- Coming-Of-Age
        new Genre("Coming-Of-Age", YAFiction, true);

        // Level 5 Child of Young Adult Fiction -- Young Adult Fantasy
        new Genre("Young Adult Fantasy", YAFiction, true);

        // Level 5 Child of Young Adult Fiction -- Young Adult Romance
        new Genre("Young Adult Romance", YAFiction, true);

        // Level 4 Child of For An Age Group -- Adult Fiction
        new Genre("Adult Fiction", forAnAgeGroup, true);

        // Organizational Only Level 3 Child of Prose -- By Setting
        Genre bySetting = new Genre("By Setting", prose, false);

        // Level 4 Child of By Setting -- Western
        new Genre("Western", bySetting, true);

        // Level 4 Child of By Setting -- Space
        new Genre("Space", bySetting, true);

        // Level 3 Child of Prose -- Novels
        Genre novels = new Genre("Novels", prose, true);

        // Level 4 Child of Novels -- Action/Adventure
        Genre actionAdventure = new Genre("Action/Adventure", novels, true);

        // Level 5 Child of Action/Adventure -- Crime
        new Genre("Crime", actionAdventure, true);

        // Level 5 Child of Action/Adventure -- Fantasy
        Genre fantasy = new Genre("Fantasy", actionAdventure, true);

        // Level 6 Child of Fantasy -- High Fantasy
        new Genre("High Fantasy", fantasy, true);

        // Level 6 Child of Fantasy -- Romantasy
        new Genre("Romantasy", fantasy, true);

        // Level 5 Child of Action/Adventure -- Heist
        new Genre("Heist", actionAdventure, true);

        // Level 5 Child of Action/Adventure -- Action Horror
        new Genre("Action Horror", actionAdventure, true);

        // Level 5 Child of Action/Adventure -- Mystery
        Genre mystery = new Genre("Mystery", actionAdventure, true);

        // Level 6 Child of Mystery -- Detective
        new Genre("Detective", mystery, true);

        // Level 6 Child of Mystery -- Whodunit
        new Genre("Whodunit", mystery, true);

        // Level 5 Child of Action/Adventure -- Sci-Fi Adventure
        new Genre("Sci-Fi Adventure", actionAdventure, true);

        // Level 5 Child of Action/Adventure -- Spy
        new Genre("Spy", actionAdventure, true);

        // Level 5 Child of Action/Adventure -- Super Hero
        new Genre("Super Hero", actionAdventure, true);

        // Level 4 Child of Novels -- Classics
        new Genre("Classics", novels, true);

        // Level 4 Child of Novels -- Historical Fiction
        Genre historicalFiction = new Genre("Historical Fiction", novels, true);

        // Level 5 Child of Historical Fiction -- Historical Fantasy
        new Genre("Historical Fantasy", historicalFiction, true);

        // Level 5 Child of Historical Fiction -- True Stories
        new Genre("True Stories", historicalFiction, true);

        // Level 5 Child of Historical Fiction -- World War II Novels
        new Genre("World War II Novels", historicalFiction, true);

        // Level 4 Child of Novels -- Romance
        Genre romance = new Genre("Romance", novels, true);

        // Level 5 Child of Romance -- Amish
        new Genre("Amish", romance, true);

        // Level 5 Child of Romance -- Queer
        new Genre("Queer", romance, true);

        // Level 5 Child of Romance -- Historical Romance
        new Genre("Historical Romance", romance, true);

        // Level 4 Child of Novels -- Sci-Fi
        new Genre("Sci-Fi", novels, true);

        // Level 4 Child of Novels -- Horror
        Genre horror = new Genre("Horror", novels, true);

        // Level 5 Child of Horror -- Supernatural
        Genre supernatural = new Genre("Supernatural", horror, true);

        // Level 6 Child of Supernatural -- Ghosts
        new Genre("Ghosts", supernatural, true);

        // Level 6 Child of Supernatural -- Cryptids
        new Genre("Cryptids", supernatural, true);

        // Level 5 Child of Horror -- Human Horrors
        new Genre("Human Horrors", horror, true);

        // Level 3 Child of Prose -- Graphic Novels
        Genre graphicNovels = new Genre("Graphic Novels", prose, true);

        // Level 4 Child of Graphic Novels -- Comic Books
        new Genre("Comic Books", graphicNovels, true);

        // Level 4 Child of Graphic Novels -- Graphic Novel Versions of Traditional Novels
        new Genre("Graphic Novel Versions of Traditional Novels", graphicNovels, true);

        // Level 1 Tree Root -- Non-Fiction
        Genre nonFiction = new Genre("Non-Fiction", null, true);
        ROOTS.add(nonFiction);

        // Level 2 Child of Non-Fiction -- Academic
        Genre academic = new Genre("Academic", nonFiction, true);

        // Level 3 Child of Academic -- Literature Review
        new Genre("Literature Review", academic, true);

        // Level 3 Child of Academic -- Reference Text
        new Genre("Reference Text", academic, true);

        // Level 3 Child of Academic -- Research Article
        new Genre("Research Article", academic, true);

        // Level 3 Child of Academic -- Textbook
        new Genre("Textbook", academic, true);

        // Level 3 Child of Academic -- Thesis
        Genre thesis = new Genre("Thesis", academic, true);

        // Level 4 Child of Thesis -- Graduate
        new Genre("Graduate", thesis, true);

        // Level 4 Child of Thesis -- Doctoral
        new Genre("Doctoral", thesis, true);

        // Level 2 Child of Non-Fiction -- Biography
        Genre biography = new Genre("Biography", nonFiction, true);

        // Level 3 Child of Biography -- Autobiography
        new Genre("Autobiography", biography, true);

        // Level 3 Child of Biography -- Memoir
        new Genre("Memoir", biography, true);

        // Level 2 Child of Non-Fiction -- Cookbook
        Genre cookbook = new Genre("Cookbook", nonFiction, true);

        // Organizational Only Level 3 Child of Cookbook -- by Region
        Genre cookbookByRegion = new Genre("by Region", cookbook, false);

        // Level 4 Child of by Region -- American South
        new Genre("American South", cookbookByRegion, true);

        // Level 4 Child of by Region -- French
        new Genre("French", cookbookByRegion, true);

        // Level 4 Child of by Region -- Italian
        new Genre("Italian", cookbookByRegion, true);

        // Level 4 Child of by Region -- Spanish
        new Genre("Spanish", cookbookByRegion, true);

        // Level 2 Child of Non-Fiction -- Collection of Essays
        new Genre("Collection of Essays", nonFiction, true);

        // Level 2 Child of Non-Fiction -- History
        Genre history = new Genre("History", nonFiction, true);

        // Organizational Only Level 3 Child of History -- by Era
        Genre historyByEra = new Genre("by Era", history, false);

        // Level 4 Child of by Era -- Ancient Babylon
        new Genre("Ancient Babylon", historyByEra, true);

        // Level 4 Child of by Era -- Ancient Greece
        new Genre("Ancient Greece", historyByEra, true);

        // Level 4 Child of by Era -- Colonial Era
        new Genre("Colonial Era", historyByEra, true);

        // Level 4 Child of by Era -- Renaissance
        new Genre("Renaissance", historyByEra, true);

        // Level 4 Child of by Era -- Roman Empire
        new Genre("Roman Empire", historyByEra, true);

        // Level 4 Child of by Era -- Roman Republic
        new Genre("Roman Republic", historyByEra, true);

        // Organizational Only Level 3 Child of History -- by Event
        Genre historyByEvent = new Genre("by Event", history, false);

        // Level 4 Child of by Event -- American Revolution
        new Genre("American Revolution", historyByEvent, true);

        // Level 4 Child of by Event -- Civil War
        new Genre("Civil War", historyByEvent, true);

        // Level 4 Child of by Event -- French Revolution
        new Genre("French Revolution", historyByEvent, true);

        // Level 4 Child of by Event -- Vietnam War
        new Genre("Vietnam War", historyByEvent, true);

        // Organizational Only Level 3 Child of History -- by Region
        Genre historyByRegion = new Genre("by Region", history, false);

        // Level 4 Child of by Region -- Africa
        Genre africa = new Genre("Africa", historyByRegion, true);

        // Level 5 Child of Africa -- East Africa
        new Genre("East Africa", africa, true);

        // Level 5 Child of Africa -- North Africa
        new Genre("North Africa", africa, true);

        // Level 5 Child of Africa -- South Africa
        new Genre("South Africa", africa, true);

        // Level 5 Child of Africa -- West Africa
        new Genre("West Africa", africa, true);

        // Level 4 Child of by Region -- America
        Genre america = new Genre("America", historyByRegion, true);

        // Level 5 Child of America -- North
        Genre north = new Genre("North", america, true);

        // Level 6 Child of North -- Canada
        new Genre("Canada", north, true);

        // Level 6 Child of North -- Mexico
        new Genre("Mexico", north, true);

        // Level 6 Child of North -- USA
        new Genre("USA", north, true);

        // Level 5 Child of America -- South
        Genre south = new Genre("South", america, true);

        // Level 6 Child of South -- Argentina
        new Genre("Argentina", south, true);

        // Level 6 Child of South -- Brazil
        new Genre("Brazil", south, true);

        // Level 6 Child of South -- Panama
        new Genre("Panama", south, true);

        // Level 6 Child of South -- Venezuela
        new Genre("Venezuela", south, true);

        // Level 4 Child of by Region -- Antarctica
        new Genre("Antarctica", historyByRegion, true);

        // Level 4 Child of by Region -- Asia
        Genre asia = new Genre("Asia", historyByRegion, true);

        // Level 5 Child of Asia -- China
        new Genre("China", asia, true);

        // Level 5 Child of Asia -- India
        new Genre("India", asia, true);

        // Level 5 Child of Asia -- Japan
        new Genre("Japan", asia, true);

        // Level 5 Child of Asia -- North Korea
        new Genre("North Korea", asia, true);

        // Level 5 Child of Asia -- Russia
        new Genre("Russia", asia, true);

        // Level 5 Child of Asia -- South Korea
        new Genre("South Korea", asia, true);

        // Level 4 Child of by Region -- Europe
        Genre europe = new Genre("Europe", historyByRegion, true);

        // Level 5 Child of Europe -- Mainland
        Genre mainland = new Genre("Mainland", europe, true);

        // Level 6 Child of Mainland -- Denmark
        new Genre("Denmark", mainland, true);

        // Level 6 Child of Mainland -- France
        new Genre("France", mainland, true);

        // Level 6 Child of Mainland -- Germany
        new Genre("Germany", mainland, true);

        // Level 6 Child of Mainland -- Netherlands
        new Genre("Netherlands", mainland, true);

        // Level 6 Child of Mainland -- Norway
        new Genre("Norway", mainland, true);

        // Level 6 Child of Mainland -- Poland
        new Genre("Poland", mainland, true);

        // Level 6 Child of Mainland -- Spain
        new Genre("Spain", mainland, true);

        // Level 6 Child of Mainland -- Sweden
        new Genre("Sweden", mainland, true);

        // Level 6 Child of Mainland -- Switzerland
        new Genre("Switzerland", mainland, true);

        // Level 5 Child of Europe -- European Islands
        Genre europeanIslands = new Genre("European Islands", europe, true);

        // Level 6 Child of European Islands -- Iceland
        new Genre("Iceland", europeanIslands, true);

        // Level 6 Child of European Islands -- UK
        Genre uk = new Genre("UK", europeanIslands, true);

        // Level 7 Child of UK -- England
        new Genre("England", uk, true);

        // Level 7 Child of UK -- Ireland
        new Genre("Ireland", uk, true);

        // Level 7 Child of UK -- Scotland
        new Genre("Scotland", uk, true);

        // Level 7 Child of UK -- Wales
        new Genre("Wales", uk, true);

        // Level 2 Child of Non-Fiction -- Science
        Genre science = new Genre("Science", nonFiction, true);

        // Level 3 Child of Science -- Biology
        Genre biology = new Genre("Biology", science, true);

        // Level 4 Child of Biology -- Anatomy/Physiology
        new Genre("Anatomy/Physiology", biology, true);

        // Level 4 Child of Biology -- Genetics
        new Genre("Genetics", biology, true);

        // Level 4 Child of Biology -- Animal Biology
        new Genre("Animal Biology", biology, true);

        // Level 3 Child of Science -- Chemistry
        new Genre("Chemistry", science, true);

        // Level 3 Child of Science -- Physics
        Genre physics = new Genre("Physics", science, true);

        // Level 4 Child of Physics -- Astronomy
        new Genre("Astronomy", physics, true);

        // Level 4 Child of Physics -- Quantum Physics
        new Genre("Quantum Physics", physics, true);

        // Level 2 Child of Non-Fiction -- Travel
        new Genre("Travel", nonFiction, true);

        // Level 2 Child of Non-Fiction -- True Crime
        Genre trueCrime = new Genre("True Crime", nonFiction, true);

        // Level 3 Child of True Crime -- Financial Crimes
        new Genre("Financial Crimes", trueCrime, true);

        // Level 3 Child of True Crime -- Violent Crimes
        new Genre("Violent Crimes", trueCrime, true);

    }

    // Prevent this class from ever being instantiated
    private GenreTree() {}

    // Search genre tree
    public static Genre findByName(String name) {

        for (Genre root: ROOTS) {

            Genre found = findByNameRecursive(root, name);
            if (found != null) {

                return found;

            }

        }

        return null;

    }

    private static Genre findByNameRecursive(Genre node, String name) {

        if (node.getName().equals(name)) {

            return node;

        }

        for (Genre child : node.getChildren()) {

            Genre found = findByNameRecursive(child, name);

            // if found
            if (found != null) {
                return found;
            }

        }

        return null;

    }

}
