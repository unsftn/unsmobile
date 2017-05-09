package rs.ac.ftn.uns.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CompetitorsContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Competitor> ITEMS = new ArrayList<Competitor>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Competitor> ITEM_MAP = new HashMap<String, Competitor>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createTeam(i));
        }
    }

    private static void addItem(Competitor item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static Competitor createTeam(int position) {
        return new Competitor(String.valueOf(position), "Item " + position, "research field :)", makeDetails(position), "Project desciption text related to some stuf/nNew line and some number is here also: " + position);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

}
