package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.dto.RequestedItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(source = "request.id", target = "requestId")
    ItemDto toDto(Item item);

    Item toItem(ItemDto item);

    List<ItemDto> itemsToItemDto(List<Item> items);

    List<ItemDtoWithBooking> itemsToItemsDtoWithBooking(List<Item> items);

    ItemDtoWithBooking toItemDtoWithBooking(Item item);

    Item toItemFromDtoWithBookings(ItemDtoWithBooking item);

    @Mapping(source = "request.id", target = "requestId")
    RequestedItemDto toRequestedItemDto(Item item);

    @Mapping(source = "request.id", target = "requestId")
    List<RequestedItemDto> toListRequestedItemDto(List<Item> items);
}
