package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestForGetDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.request.utils.RequestApiPathConstants;

import javax.validation.Valid;
import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = RequestApiPathConstants.REQUEST_PATH)
public class ItemRequestController {
    private final RequestService service;

    @PostMapping
    public ResponseEntity<ItemRequestDto> add(@Valid @RequestBody ItemRequestDto request, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.status(201).body(service.add(request, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestForGetDto>> getByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(service.getByUser(userId));
    }

    @GetMapping(path = RequestApiPathConstants.ALL)
    public ResponseEntity<List<ItemRequestDto>> getAll(@RequestParam(defaultValue = "0") Long from,
                                                       @RequestParam(defaultValue = "100") Long size,
                                                       @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(service.getAll(from, size, userId));
    }

    @GetMapping(path = RequestApiPathConstants.BY_ID)
    public ResponseEntity<ItemRequestForGetDto> getById(@PathVariable Long requestId, @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ResponseEntity.ok(service.getById(requestId, userId));
    }
}
