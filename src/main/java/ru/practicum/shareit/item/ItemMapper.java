package ru.practicum.shareit.item;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.dto.UserDto;

@Component
public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        UserDto ownerDto = UserMapper.toUserDto(item.getOwner());
        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                ownerDto,
                item.getAvailable(),
                item.getRequest()
        );
    }
}
