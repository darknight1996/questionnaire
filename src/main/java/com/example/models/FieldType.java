package com.example.models;

public enum FieldType {

    SINGLE_LINE_TEXT("Single Line Text"),
    MULTI_LINE_TEXT("Multiline Text"),
    RADIO_BUTTON("Radio Button"),
    CHECKBOX("Checkbox"),
    COMBOBOX("Combobox"),
    DATE("Date");

    private String name;

    FieldType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
