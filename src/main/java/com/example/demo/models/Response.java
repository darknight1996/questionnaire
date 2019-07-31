package com.example.demo.models;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "response")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ElementCollection()
    @CollectionTable(name = "answers")
    @MapKeyColumn(name = "field_id")
    @MapKeyClass(Long.class)
    @Column(name = "value")
    private Map<Long, String> map = new HashMap<>();

    public Response() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, String> getMap() {
        return map;
    }

    public void setMap(Map<Long, String> map) {
        this.map = map;
    }
}
