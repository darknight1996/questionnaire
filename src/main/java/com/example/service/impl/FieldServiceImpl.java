package com.example.service.impl;

import com.example.models.Field;
import com.example.models.FieldType;
import com.example.repository.FieldRepo;
import com.example.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    @Autowired
    private FieldRepo fieldRepo;

    @Override
    public void addField(Field field) {
        fieldRepo.save(field);
    }

    @Override
    public List<Field> getAllFields() {
        return fieldRepo.findByOrderById();
    }

    @Override
    public List<Field> getActiveFields() {
        return fieldRepo.getAllByIsActiveIsTrue();
    }

    @Override
    public void deleteField(Long id) {
        fieldRepo.deleteById(id);
    }

    @Override
    public void editField(Long id, String label, FieldType type, List<String> options, boolean required, boolean active) {
        Field field = fieldRepo.findOneById(id);
        field.setLabel(label);
        field.setType(type);
        if (type == FieldType.RADIO_BUTTON || type == FieldType.COMBOBOX) {
            field.setOptions(options);
        }
        field.setRequired(required);
        field.setActive(active);
        fieldRepo.save(field);
    }

    @Override
    public Field getField(Long id) {
        return fieldRepo.findOneById(id);
    }

    @Override
    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        getActiveFields().forEach(field -> labels.add(field.getLabel()));
        return labels;
    }
}
