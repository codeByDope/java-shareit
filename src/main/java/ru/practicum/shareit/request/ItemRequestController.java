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
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestForGetDto>> getByUser(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return null;
    }

    @GetMapping(path = RequestApiPathConstants.ALL)
    public ResponseEntity<List<ItemRequestDto>> getAll(@RequestParam Long from, @RequestParam Long size) {
        return ResponseEntity.ok(service.getAll(from, size));
    }

    @GetMapping(path = RequestApiPathConstants.BY_ID)
    public ResponseEntity<ItemRequestForGetDto> getById(@PathVariable Long requestId) {
        return null;
    }
}
