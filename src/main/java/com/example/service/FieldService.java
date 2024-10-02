package com.example.service;

import com.example.models.Field;

import java.util.List;

public interface FieldService {

    void addField(final Field field);

    List<Field> getAllFields();

    List<Field> getActiveFields();

    void deleteField(final Long id);

    void editField(final Field field);

    List<String> getLabels();

}
