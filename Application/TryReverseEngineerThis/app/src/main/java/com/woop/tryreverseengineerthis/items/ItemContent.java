package com.woop.tryreverseengineerthis.items;

import com.woop.tryreverseengineerthis.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple item content for recycler view
 */
public class ItemContent {

    public static final List<ClassItem> ITEMS = new ArrayList<ClassItem>();
    public static final Map<String, ClassItem> ITEM_MAP = new HashMap<String, ClassItem>();

    private static final int COUNT = 4;

    static {
        addItem();
    }

    /*
     * Add the class items to the item map
     */
    private static void addItem() {
        ClassItem item;
        item = new ClassItem("1", "COMPSCI 702", "", R.drawable.android_security);
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        item = new ClassItem("2", "SOFTENG 700", "", R.drawable.research);
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        item = new ClassItem("3", "SOFTENG 750", "", R.drawable.software_development_methodology);
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        item = new ClassItem("4", "SOFTENG 751", "", R.drawable.datacentre);
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class ClassItem {
        public final String id;
        public final String content;
        public final int imageId;
        public final String details;

        public ClassItem(String id, String content, String details, int imageId) {
            this.id = id; this.content = content; this.details = details; this.imageId = imageId;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
