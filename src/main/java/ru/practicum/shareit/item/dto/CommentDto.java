package ru.practicum.shareit.item.dto;

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
public class CommentDto {
    private Long id;

    @NotNull
    @NotBlank
    String text;

    ItemDto item;

    UserDto author;

    LocalDateTime created;

    String authorName;
}
