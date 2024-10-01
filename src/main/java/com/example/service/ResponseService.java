package com.example.service;

import com.example.models.Response;

import java.util.List;
import java.util.Map;

public interface ResponseService {

    void addResponse(Map<Long, String> map);

    List<Response> getResponses();
}
