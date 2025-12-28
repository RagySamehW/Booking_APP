package com.example.BookingService.Domain.model;

public enum BookingStatus {

    PENDING(1),
    RESCHEDULED(2),
    COMPLETED(3),
    CANCELLED(4);

    private final int code;

    BookingStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static BookingStatus fromCode(int code) {
        for (BookingStatus s : values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
