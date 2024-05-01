package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Builder
@AllArgsConstructor
public class BookingDto {
    private Long id;
    @NotNull(message = "Нельзя забронировать несуществующий предмет")
    private Item item;
    @NotNull(message = "Несуществующий пользователь не может совершать бронирование")
    private UserDto booker;
    @NotNull(message = "Для бронирования нужно выбрать дату начала")
    private LocalDate start;
    @NotNull(message = "Для бронирования нужно выбрать дату конца")
    private LocalDate end;
    private BookingStatus status;
}
