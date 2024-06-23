package ru.practicum.shareit.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

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
    private UserDto requester;
    private LocalDateTime created;
    private List<RequestedItemDto> items;
}
