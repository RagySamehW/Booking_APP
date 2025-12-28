package com.example.BookingService.Data.mapper;

import com.example.BookingService.Data.entity.BranchServiceEntity;
import com.example.BookingService.Domain.model.BranchServiceModel;
import org.springframework.stereotype.Component;

@Component
public class BranchServiceMapper {

    public BranchServiceModel toDomain(BranchServiceEntity entity) {
        BranchServiceModel model = new BranchServiceModel();
        model.setBranch_id(entity.getBranch_id());
        model.setService_id(entity.getService_id());
        model.setCapacity_per_day(entity.getCapacity_per_day());
        return model;
    }
}