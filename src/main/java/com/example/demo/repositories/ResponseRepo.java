package com.example.demo.repositories;

import com.example.demo.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepo extends JpaRepository<Response, Long> {
}
