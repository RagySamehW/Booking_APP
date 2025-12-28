package com.example.BookingService.Data.mapper;
import com.example.BookingService.Data.entity.BookingEntity;
import com.example.BookingService.Domain.model.BookingModel;
import com.example.BookingService.Domain.model.BookingStatus;
import com.example.BookingService.Domain.dto.BookingRequestDTO; // <<< Make sure to import this
import com.example.BookingService.Domain.dto.BookingResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {
    public BookingEntity toEntity(BookingRequestDTO dto) {
        BookingEntity entity = new BookingEntity(); // ✔ no-args

        entity.setService_id(dto.getService_id());
        entity.setCar_id(dto.getCar_id());
        entity.setBranch_id(dto.getBranch_id());
        entity.setComments(dto.getComments());

        return entity;
    }
    public BookingModel toDomain(BookingEntity entity) {
        BookingModel model = new BookingModel();
        model.setId(entity.getBooking_id());
        model.setService_id(entity.getService_id());
        model.setCar_id(entity.getCar_id());
        model.setBranch_id(entity.getBranch_id());
        model.setBooking_date(entity.getBooking_date());


        if (entity.getStatus_id() != null) {
            model.setStatus(BookingStatus.fromCode(entity.getStatus_id().intValue()));
        }
        model.setComments(entity.getComments());
        return model;
    }

    public BookingEntity toEntity(BookingModel model) {
        BookingEntity entity = new BookingEntity();
        entity.setBooking_id(model.getId());
        entity.setService_id(model.getService_id());
        entity.setCar_id(model.getCar_id());
        entity.setBranch_id(model.getBranch_id());
        entity.setBooking_date(model.getBooking_date());
        entity.setComments(model.getComments());

        if (model.getStatus() != null) {
            entity.setStatus_id(
                    (long) model.getStatus().getCode()
            );
        }
        return entity;
    }

    public BookingResponseDTO toDto(BookingModel model) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(model.getId());
        dto.setService_id(model.getService_id());
        dto.setCar_id(model.getCar_id());
        dto.setBranch_id(model.getBranch_id());
        dto.setBooking_date(model.getBooking_date());
        dto.setStatus(model.getStatus());
        dto.setComments(model.getComments());
        return dto;
    }
}