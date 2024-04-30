package ru.practicum.shareit.request.dto;

import lombok.Builder;
import ru.practicum.shareit.user.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Builder
public class ItemRequestDto {
    private Long id;
    @NotNull(message = "Текст запроса не может быть null")
    @NotBlank(message = "Текст запроса не может быть пустым")
    private String description;
    @NotNull(message = "Пользователь, создающий запрос должен существовать")
    private User requester;
    private LocalDateTime created;
}
