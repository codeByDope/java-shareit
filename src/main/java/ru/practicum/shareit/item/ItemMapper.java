package ru.practicum.shareit.item;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserMapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface ItemMapper {
    ItemDto toDto(Item item);
}
