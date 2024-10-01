package com.example.repository;

import com.example.models.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepo extends JpaRepository<Field, Long> {

    Field findOneById(Long id);

    List<Field> findByOrderById();

    List<Field> getAllByIsActiveIsTrue();

}
