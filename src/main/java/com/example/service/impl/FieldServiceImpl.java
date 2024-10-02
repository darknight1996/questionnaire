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

    private final FieldRepo fieldRepo;

    public FieldServiceImpl(final FieldRepo fieldRepo) {
        this.fieldRepo = fieldRepo;
    }

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
    public void editField(final Field field) {
        fieldRepo.save(field);
    }

    @Override
    public List<String> getLabels() {
        List<String> labels = new ArrayList<>();
        getActiveFields().forEach(field -> labels.add(field.getLabel()));
        return labels;
    }
}
