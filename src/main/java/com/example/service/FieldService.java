package com.example.service;

import com.example.models.Field;
import com.example.models.FieldType;

import java.util.List;

public interface FieldService {

    void addField(Field field);

    List<Field> getAllFields();

    List<Field> getActiveFields();

    void deleteField(Long id);

    void editField(Long id, String label, FieldType type, List<String> options, boolean required, boolean active);

    Field getField(Long id);

    List<String> getLabels();

}
