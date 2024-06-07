package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.booking.dto.BookingDtoForItemWithBookings;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoWithBooking {
    private Long id;

    private String name;

    private String description;

    private UserDto owner;

    private Boolean available;

    private ItemRequestDto request;

    private BookingDtoForItemWithBookings nextBooking;

    private BookingDtoForItemWithBookings lastBooking;

    private List<CommentDto> comments;
}
