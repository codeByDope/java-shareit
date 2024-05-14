package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
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
    public BookingDtoForAnswer add(@Valid @RequestBody BookingDto booking, @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        return service.add(booking, userId);
    }

    @PatchMapping(BookingApiPathConstants.BOOKING_ID_PATH)
    public BookingDtoForAnswer approve(@PathVariable Long bookingId, @RequestParam Boolean approved, @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        return service.approve(bookingId, approved, userId);
    }

    @GetMapping(BookingApiPathConstants.BOOKING_ID_PATH)
    public BookingDtoForAnswer getById(@PathVariable Long bookingId, @RequestHeader("X-Sharer-User-Id") Long userId) throws AccessDeniedException {
        return service.getById(bookingId, userId);
    }

    @GetMapping
    public List<BookingDtoForAnswer> getByUser(@RequestParam(required = false, defaultValue = "ALL") String state, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getByUser(state, userId);
    }

    @GetMapping(BookingApiPathConstants.OWNER)
    public List<BookingDtoForAnswer> getByOwner(@RequestParam(required = false, defaultValue = "ALL") String state, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return service.getByOwner(state, userId);
    }
}
