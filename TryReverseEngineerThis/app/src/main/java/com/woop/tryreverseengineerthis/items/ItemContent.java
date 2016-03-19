package com.woop.tryreverseengineerthis.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ItemContent {

    public static final List<ClassItem> ITEMS = new ArrayList<ClassItem>();
    public static final Map<String, ClassItem> ITEM_MAP = new HashMap<String, ClassItem>();

    private static final int COUNT = 4;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(ClassItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ClassItem createDummyItem(int position) {
        return new ClassItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ClassItem {
        public final String id;
        public final String content;
        public final String details;

        public ClassItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
