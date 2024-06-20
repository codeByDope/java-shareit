package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoForAnswer;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.booking.utils.BookingApiPathConstants;

import javax.validation.Valid;
import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping(path = BookingApiPathConstants.BOOKINGS_PATH)
public class BookingController {
    private final BookingService service;

    @PostMapping
    public ResponseEntity<BookingDtoForAnswer> add(@Valid @RequestBody BookingDto booking,
                                                   @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        return ResponseEntity.status(201).body(service.add(booking, userId));
    }

    @PatchMapping(BookingApiPathConstants.BOOKING_ID_PATH)
    public ResponseEntity<BookingDtoForAnswer> approve(@PathVariable Long bookingId,
                                                       @RequestParam Boolean approved,
                                                       @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        return ResponseEntity.ok(service.approve(bookingId, approved, userId));
    }

    @GetMapping(BookingApiPathConstants.BOOKING_ID_PATH)
    public ResponseEntity<BookingDtoForAnswer> getById(@PathVariable Long bookingId,
                                                       @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        return ResponseEntity.ok(service.getById(bookingId, userId));
    }

    @GetMapping
    public ResponseEntity<List<BookingDtoForAnswer>> getByUser(@RequestParam(defaultValue = "0") Long from,
                                                               @RequestParam(defaultValue = "100") Long size,
                                                               @RequestParam(required = false, defaultValue = "ALL") String state,
                                                               @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(service.getByUser(from, size, state, userId));
    }

    @GetMapping(BookingApiPathConstants.OWNER)
    public ResponseEntity<List<BookingDtoForAnswer>> getByOwner(@RequestParam(defaultValue = "0") Long from,
                                                                @RequestParam(defaultValue = "100") Long size,
                                                                @RequestParam(required = false, defaultValue = "ALL") String state,
                                                                @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(service.getByOwner(from, size, state, userId));
    }
}
