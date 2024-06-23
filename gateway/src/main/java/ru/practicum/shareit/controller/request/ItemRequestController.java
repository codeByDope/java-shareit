package ru.practicum.shareit.controller.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.RequestClient;
import ru.practicum.shareit.controller.ParamValidator;
import ru.practicum.shareit.dto.ItemRequestDto;

import javax.validation.Valid;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = RequestApiPathConstants.REQUEST_PATH)
public class ItemRequestController {
    private final RequestClient client;

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody ItemRequestDto request, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на запрос)) {}", request);
        return client.add(userId, request);
    }

    @GetMapping
    public ResponseEntity<Object> getByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение всех запросов пользователя с ID {}", userId);
        return client.getAllUserRequests(userId);
    }

    @GetMapping(path = RequestApiPathConstants.ALL)
    public ResponseEntity<Object> getAll(@RequestParam(defaultValue = "0") Long from,
                                         @RequestParam(defaultValue = "100") Long size,
                                         @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос на получение всех запросов других пользователей");
        ParamValidator.checkPagination(from, size);
        return client.getAll(userId, from, size);
    }

    @GetMapping(path = RequestApiPathConstants.BY_ID)
    public ResponseEntity<Object> getById(@PathVariable Long requestId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        log.info("Запрос запроса)) по ID {}", requestId);
        return client.getById(userId, requestId);
    }
}
