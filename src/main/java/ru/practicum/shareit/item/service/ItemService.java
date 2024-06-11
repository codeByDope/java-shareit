package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface ItemService {

    ItemDto add(ItemDto item, Long ownerId);

    ItemDto update(ItemDtoForUpdate item, Long ownerId) throws AccessDeniedException;

    ItemDtoWithBooking getById(Long id, Long userId);

    List<ItemDtoWithBooking> getAllByOwner(Long ownerId);

    List<ItemDto> search(String text);

    CommentDto addComment(Long itemId, Long userId, CommentDto commentDto);

    List<RequestedItemDto> getAllByRequestId(Long requestId);
}
