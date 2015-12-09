package com.introtoandroid.simplemasterdetailflow.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewsContent {
    public static List<NewsItem> ITEMS = new ArrayList<>();
    public static Map<String, NewsItem> ITEM_MAP = new HashMap<>();

    public static String sports = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque tristique dolor diam, at lobortis libero imperdiet gravida. Fusce facilisis eget neque vestibulum dignissim. Suspendisse placerat risus fermentum scelerisque fringilla. Ut molestie lectus et libero suscipit, non sagittis nibh vehicula. Nullam interdum velit eget magna commodo, et dictum nunc efficitur. Pellentesque pharetra tortor diam, vel interdum diam dictum et. Aliquam congue mi diam, ac elementum orci semper nec. Sed tincidunt felis quam, ac dapibus turpis euismod nec. Cras sit amet lorem erat. Aliquam metus sapien, varius ac risus non, rhoncus blandit metus. Nunc consectetur a quam et mattis. Vivamus vulputate massa ut feugiat pharetra. Donec eu egestas elit.";
    public static String finance = "Nullam vehicula, lorem et maximus consequat, velit felis ultrices lacus, rhoncus dignissim turpis magna vitae nulla. Praesent sodales leo nec dui porta condimentum. Sed est diam, interdum eu odio at, porttitor tempor tellus. Donec metus leo, lacinia eu neque tempor, auctor dictum dolor. Sed hendrerit elementum volutpat. Duis et urna rutrum, aliquet elit sit amet, tincidunt risus. Ut accumsan erat sem, vel elementum nulla facilisis eget. Nam id nibh magna.";
    public static String world = "Cras aliquam vel massa in eleifend. Cras odio nibh, porta fermentum dolor nec, mollis ultricies orci. Aliquam egestas ante enim, nec lobortis mi fringilla id. Curabitur volutpat, libero sit amet bibendum imperdiet, magna purus efficitur risus, ac malesuada urna dolor et metus. Nam commodo viverra euismod. Phasellus imperdiet odio ipsum. Sed ullamcorper hendrerit erat, et consequat ante volutpat eu.";

    static {
        addItem(new NewsItem("1", "Sports", sports));
        addItem(new NewsItem("2", "Finance", finance));
        addItem(new NewsItem("3", "World", world));
    }

    private static void addItem(NewsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class NewsItem {
        public String id;
        public String title;
        public String content;

        public NewsItem(String id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        @Override
        public String toString() {
            return title;
        }
    }
}
