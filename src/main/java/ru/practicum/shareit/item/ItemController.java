package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.item.utils.ItemApiPathConstants;

import javax.validation.constraints.Positive;
import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(ItemApiPathConstants.ITEMS_PATH)
public class ItemController {
    private final ItemService service;

    @PostMapping
    public ItemDto add(@RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return service.add(item, ownerId);
    }

    @PatchMapping(ItemApiPathConstants.ITEM_ID_PATH)
    public ItemDto update(@RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Long ownerId, @Positive @PathVariable Long itemId) throws AccessDeniedException {
        item.setId(itemId);
        return service.update(item, ownerId);
    }

    @GetMapping
    public List<ItemDto> getAllBy(@RequestHeader("X-Sharer-User-Id") Long ownerId) {
        return service.getAllByOwner(ownerId);
    }

    @GetMapping(ItemApiPathConstants.ITEM_ID_PATH)
    public ItemDto getById(@PathVariable Long itemId) {
        return service.getById(itemId);
    }

    @GetMapping(ItemApiPathConstants.SEARCH_ITEMS_PATH)
    public List<ItemDto> search(@RequestParam String text) {
        return service.search(text);
    }


}
