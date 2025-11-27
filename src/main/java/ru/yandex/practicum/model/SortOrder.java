package ru.yandex.practicum.model;

public enum SortOrder {
    ASCENDING, DESCENDING;

    public static SortOrder from(String order) {
        switch (order.toLowerCase()) {
            case "ascending":
            case "ask":
                return ASCENDING;
            case "descending":
            case "desk":
                return DESCENDING;
            default: return null;
        }
    }
}