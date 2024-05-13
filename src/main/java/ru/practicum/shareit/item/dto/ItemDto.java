package ru.practicum.shareit.item.dto;

import lombok.*;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * TODO Sprint add-controllers.
 */
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    private Long id;

    @NotNull(message = "Предмет не может иметь имя, статус доступа и описания со значением null")
    @NotBlank
    private String name;

    @NotNull(message = "Предмет не может иметь имя, статус доступа и описания со значением null")
    @NotBlank
    private String description;

    private UserDto owner;

    @NotNull(message = "Предмет не может иметь статус доступа со значением null")
    private Boolean available;

    private ItemRequest request;
}
