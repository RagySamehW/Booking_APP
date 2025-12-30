package com.example.demo.domain.model;

import lombok.Data;

@Data
public class Branch {
    private Long id;
    private Long automotiveId;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
}