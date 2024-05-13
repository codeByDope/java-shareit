package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForUpdate;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ItemService {

    ItemDto add(ItemDto item, Long ownerId);

    ItemDto update(ItemDtoForUpdate item, Long ownerId) throws AccessDeniedException;

    ItemDto getById(Long id);

    List<ItemDto> getAllByOwner(Long ownerId);

    List<ItemDto> search(String text);
}
