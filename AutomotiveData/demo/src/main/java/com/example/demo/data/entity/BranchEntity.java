package com.example.demo.data.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("branches")
public class BranchEntity {

    @Id
    private Long branch_id;

    private Integer capacity;
    @Column("automotive_id")
    private Long automotiveId;
    private String branch_name;
    private String branch_address;
    private String email;
    private String phone_number;
}