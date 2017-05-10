package rs.ac.ftn.uns.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rs.ac.ftn.uns.model.ABItem;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ABItemContent {

    /**
     * An array of sample (dummy) items.
     */ //// TODO: 9.5.2017. Tu ocigledno ima lista
    public static final List<ABItem> ITEMS = new ArrayList<ABItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */ // // TODO: 9.5.2017. A tu ocigledno ima mapa, videti kako koristi!:D
    public static final Map<Integer, ABItem> ITEM_MAP = new HashMap<Integer, ABItem>();

    //// TODO: 9.5.2017. Ocigledno bi bilo lepo da samo iskoristim ovu metodu.
    public static void addItem(ABItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }

    public static void cleanItems(){
        ITEMS.clear();
        ITEM_MAP.clear();
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
