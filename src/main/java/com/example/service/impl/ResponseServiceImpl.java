package com.example.service.impl;

import com.example.models.Response;
import com.example.repository.ResponseRepo;
import com.example.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ResponseServiceImpl implements ResponseService {

    @Autowired
    ResponseRepo responseRepo;

    @Override
    public void addResponse(Map<Long, String> map) {
        Response response = new Response();
        response.setMap(map);
        responseRepo.save(response);
    }

    @Override
    public List<Response> getResponses() {
        return responseRepo.findAll();
    }
}
