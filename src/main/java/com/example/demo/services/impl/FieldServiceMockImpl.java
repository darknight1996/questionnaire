package com.example.demo.services.impl;

import com.example.demo.models.Field;
import com.example.demo.models.FieldType;
import com.example.demo.services.FieldService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("mock")
public class FieldServiceMockImpl /*implements FieldService*/ {
    //@Override
    public void addField(Field field) {

    }

    //@Override
    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>(3);
        fields.add(new Field("Full Name", FieldType.SINGLE_LINE_TEXT, true, true));
        fields.add(new Field("Email", FieldType.SINGLE_LINE_TEXT, true, true));
        fields.add(new Field("Sex", FieldType.RADIO_BUTTON, false, true));
        return fields;
    }
}
