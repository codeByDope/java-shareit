package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForUpdate;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.service.UserService;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository repository;
    private final ItemMapper mapper = ItemMapper.INSTANCE;

    @Override
    public ItemDto add(ItemDto item, Long ownerId) {
        item.setOwner(userService.getById(ownerId));
        return mapper.toDto(repository.save(mapper.toItem(item)));
    }

    @Override
    public ItemDto update(ItemDtoForUpdate updatedItem, Long ownerId) throws AccessDeniedException {
        Long id = updatedItem.getId();

        Item existingItem = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Предмета с таким ID не существует"));

        if (!existingItem.getOwner().getId().equals(ownerId)) {
            throw new AccessDeniedException("Вещь может редактировать только владелец");
        }

        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getAvailable() != null) {
            existingItem.setAvailable(updatedItem.getAvailable());
        }

        return mapper.toDto(repository.save(existingItem));
    }


    @Override
    public ItemDto getById(Long id) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Предмета с таким ID не существует"));
        return mapper.toDto(item);
    }

    @Override
    public List<ItemDto> getAllByOwner(Long id) {
        List<Item> items = repository.findAllByOwnerId(id);
        return mapper.itemsToItemDto(items);
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> items = repository.search(text);
        return mapper.itemsToItemDto(items);
    }
}
