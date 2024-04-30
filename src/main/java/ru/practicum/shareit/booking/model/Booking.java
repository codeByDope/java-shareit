package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@Setter
@Builder
public class Booking {
    private Long id;
    @NotNull(message = "Нельзя забронировать несуществующий предмет")
    private Item item;
    @NotNull(message = "Несуществующий пользователь не может совершать бронирование")
    private User booker;
    @NotNull(message = "Для бронирования нужно выбрать дату начала")
    private LocalDate start;
    @NotNull(message = "Для бронирования нужно выбрать дату конца")
    private LocalDate end;
    private BookingStatus status;
}
