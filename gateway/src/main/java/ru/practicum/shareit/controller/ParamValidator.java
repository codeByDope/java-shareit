package ru.practicum.shareit.controller;

import ru.practicum.shareit.controller.booking.State;
import ru.practicum.shareit.dto.BookingDto;

import javax.validation.ValidationException;
import java.time.LocalDateTime;

public class ParamValidator {
    public static void checkPagination(Long from, Long size) {
        if (from < 0 || size < 0) {
            throw new ValidationException("from и size не могут быть меньше 0");
        }
    }

    public static void checkDates(BookingDto bookingDto) {
        LocalDateTime start = bookingDto.getStart();
        LocalDateTime end = bookingDto.getEnd();
        LocalDateTime now = LocalDateTime.now();
        if (start.isAfter(end)) {
            throw new ValidationException("Конец должен быть после старта!");
        }
        if (start.isEqual(end)) {
            throw new ValidationException("Конец не должен быть равен старту!");
        }
        if (start.isBefore(now)) {
            throw new ValidationException("Бронировать задним числом запрещено!");
        }
        if (end.isBefore(now)) {
            throw new ValidationException("Бронировать задним числом запрещено!");
        }
    }

    public static void checkState(String state) {
        try {
            switch (State.valueOf(state)) {
                case ALL:
                    break;
                case CURRENT:
                    break;
                case PAST:
                    break;
                case FUTURE:
                    break;
                case WAITING:
                    break;
                case REJECTED:
                    break;
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown state: " + state);
        }
    }
}
