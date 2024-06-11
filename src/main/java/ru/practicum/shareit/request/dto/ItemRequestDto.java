package ru.practicum.shareit.request.dto;

import lombok.*;
import ru.practicum.shareit.user.dto.UserDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemRequestDto {
    private Long id;
    @NotNull(message = "Текст запроса не может быть null")
    @NotBlank(message = "Текст запроса не может быть пустым")
    private String description;
    @NotNull(message = "Пользователь, создающий запрос должен существовать")
    private UserDto requester;
    private LocalDateTime created;
}
