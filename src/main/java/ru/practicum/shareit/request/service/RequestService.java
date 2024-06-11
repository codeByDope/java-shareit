package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestForGetDto;

import java.util.List;

public interface RequestService {
    ItemRequestDto add(ItemRequestDto requestDto, Long userId);

    List<ItemRequestForGetDto> getByUser(Long userId);

    List<ItemRequestDto> getAll(Long from, Long size);

    ItemRequestForGetDto getById(Long requestId);
}
