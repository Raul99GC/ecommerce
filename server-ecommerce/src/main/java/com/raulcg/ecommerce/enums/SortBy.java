package com.raulcg.ecommerce.enums;

public enum SortBy {
    PRICE_LOW_TO_HIGH("price-lowtohigh"),
    PRICE_HIGH_TO_LOW("price-hightolow"),
    TITLE_A_TO_Z("title-atoz"),
    TITLE_Z_TO_A("title-ztoa");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static SortBy fromValue(String value) {
        for (SortBy sortBy : values()) {
            if (sortBy.value.equals(value.toLowerCase())) {
                return sortBy;
            }
        }
        throw new IllegalArgumentException("Invalid sortBy value: " + value);
    }
}