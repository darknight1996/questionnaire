package com.example.repository;

import com.example.models.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepo extends JpaRepository<Response, Long> {
}
