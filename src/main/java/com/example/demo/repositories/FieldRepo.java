package com.example.demo.repositories;

import com.example.demo.models.Field;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepo extends JpaRepository<Field, Long> {

    Field findOneById(Long id);

    List<Field> findByOrderById();

    List<Field> getAllByIsActiveIsTrue();

}
