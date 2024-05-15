package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDto toDto(Item item);

    Item toItem(ItemDto item);

    List<ItemDto> itemsToItemDto(List<Item> items);

    List<ItemDtoWithBooking> itemsToItemsDtoWithBooking(List<Item> items);

    ItemDtoWithBooking toItemDtoWithBooking(Item item);

    Item toItemFromDtoWithBookings(ItemDtoWithBooking item);
}
