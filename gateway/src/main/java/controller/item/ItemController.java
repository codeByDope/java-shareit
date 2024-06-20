package controller.item;

import client.ItemClient;
import controller.ParamValidator;
import dto.CommentDto;
import dto.ItemDto;
import dto.ItemDtoForUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.nio.file.AccessDeniedException;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(ItemApiPathConstants.ITEMS_PATH)
public class ItemController {
    private final ItemClient client;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Запрос на добавление предмета {}", item);
        return client.add(ownerId, item);
    }

    @PatchMapping(ItemApiPathConstants.ITEM_ID_PATH)
    public ResponseEntity<Object> update(@RequestBody ItemDtoForUpdate item, @RequestHeader("X-Sharer-User-Id") Long ownerId, @Positive @PathVariable Long itemId) throws AccessDeniedException {
        log.info("Запрос на добавление предмета с ID {}", itemId);
        return client.update(ownerId, itemId, item);
    }

    @GetMapping
    public ResponseEntity<Object> getAllBy(@RequestParam(defaultValue = "0") Long from,
                                           @RequestParam(defaultValue = "100") Long size,
                                           @RequestHeader("X-Sharer-User-Id") Long ownerId) {
        log.info("Запрос на получение всех вещей пользователя с ID {}", ownerId);
        ParamValidator.checkPagination(from, size);
        return client.findAllUserItems(ownerId, from, size);
    }

    @GetMapping(ItemApiPathConstants.ITEM_ID_PATH)
    public ResponseEntity<Object> getById(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение вещи с ID {}", itemId);
        return client.findById(userId, itemId);
    }

    @GetMapping(ItemApiPathConstants.SEARCH_ITEMS_PATH)
    public ResponseEntity<Object> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @RequestParam(defaultValue = "0") Long from,
                                         @RequestParam(defaultValue = "100") Long size,
                                         @RequestParam String text) {
        log.info("Запрос на поиск {}", text);
        return client.search(userId, text, from, size);
    }

    @PostMapping(ItemApiPathConstants.COMMENTS)
    public ResponseEntity<Object> addComment(@PathVariable Long itemId, @RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody CommentDto comment) {
        log.info("Запрос на комментарий {}", comment);
        return client.addComment(userId, itemId, comment);
    }
}
