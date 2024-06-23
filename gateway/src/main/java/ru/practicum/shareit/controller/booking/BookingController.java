package ru.practicum.shareit.controller.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.BookingClient;
import ru.practicum.shareit.controller.ParamValidator;
import ru.practicum.shareit.dto.BookingDto;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = BookingApiPathConstants.BOOKINGS_PATH)
public class BookingController {
    private final BookingClient client;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody BookingDto booking,
                                      @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        log.info("Запрос на бронирование {}", booking);
        ParamValidator.checkDates(booking);
        return client.bookItem(userId, booking);
    }

    @PatchMapping(BookingApiPathConstants.BOOKING_ID_PATH)
    public ResponseEntity<Object> approve(@PathVariable Long bookingId,
                                          @RequestParam Boolean approved,
                                          @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        log.info("Запрос на работу со статусом бронирования ID - {}", bookingId);
        return client.approve(userId, bookingId, approved);
    }

    @GetMapping(BookingApiPathConstants.BOOKING_ID_PATH)
    public ResponseEntity<Object> getById(@PathVariable Long bookingId,
                                          @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        log.info("Запрос на получение бронирования с ID {}", bookingId);
        return client.getBooking(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getByUser(@RequestParam(defaultValue = "0") Long from,
                                            @RequestParam(defaultValue = "100") Long size,
                                            @RequestParam(required = false, defaultValue = "ALL") String state,
                                            @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение бронирований пользователя с ID {}", userId);
        ParamValidator.checkState(state);
        ParamValidator.checkPagination(from, size);
        return client.getBookings(userId, State.valueOf(state), from, size);
    }

    @GetMapping(BookingApiPathConstants.OWNER)
    public ResponseEntity<Object> getByOwner(@RequestParam(defaultValue = "0") Long from,
                                             @RequestParam(defaultValue = "100") Long size,
                                             @RequestParam(required = false, defaultValue = "ALL") String state,
                                             @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение бронирований владельцем с ID {}", userId);
        ParamValidator.checkState(state);
        ParamValidator.checkPagination(from, size);
        return client.getOwnerBookings(userId, State.valueOf(state), from, size);
    }
}
