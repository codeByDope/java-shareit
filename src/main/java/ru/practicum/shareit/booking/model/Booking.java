package ru.practicum.shareit.booking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Getter
@Setter
@Builder
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Для бронирования нужно выбрать дату начала")
    @Column(name = "start_date")
    private LocalDate start;

    @NotNull(message = "Для бронирования нужно выбрать дату конца")
    @Column(name = "end_date")
    private LocalDate end;

    @NotNull(message = "Нельзя забронировать несуществующий предмет")
    @ManyToOne
    @JoinColumn(name = "item_id") // Указываем имя столбца
    private Item item;

    @NotNull(message = "Несуществующий пользователь не может совершать бронирование")
    @ManyToOne
    @JoinColumn(name = "booker_id") // Указываем имя столбца
    private User booker;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
