package com.example.demo.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String label;

    @Enumerated(EnumType.STRING)
    private FieldType type;

    private boolean required;

    private boolean isActive;

    @ElementCollection
    private List<String> options;

    public Field() {
    }

    public Field(String label, FieldType type, Boolean required, Boolean isActive) {
        this.label = label;
        this.type = type;
        this.required = required;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getOptionsForFront() {
        StringBuilder string = new StringBuilder();
        options.forEach(option -> string.append(option + "\n"));
        return string.toString();
    }
}
