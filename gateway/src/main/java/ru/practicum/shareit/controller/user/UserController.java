package ru.practicum.shareit.controller.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.client.UserClient;
import ru.practicum.shareit.dto.UserDto;
import ru.practicum.shareit.dto.UserDtoForUpdate;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(UserApiPathConstants.USERS_PATH)
public class UserController {
    private final UserClient client;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        log.info("Запрос на получение всех пользователей");
        return client.findAll();
    }

    @GetMapping(UserApiPathConstants.USER_ID)
    public ResponseEntity<Object> getById(@Positive @PathVariable Long userId) {
        log.info("Запрос на получение пользователя с ID {}", userId);
        return client.findById(userId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос на создание пользователя {}", userDto);
        return client.add(userDto);
    }

    @PatchMapping(UserApiPathConstants.USER_ID)
    public ResponseEntity<Object> update(@Valid @RequestBody UserDtoForUpdate userDto, @Positive @PathVariable Long userId) {
        log.info("Запрос на обновление пользователя с ID {}", userId);
        return client.update(userId, userDto);
    }

    @DeleteMapping(UserApiPathConstants.USER_ID)
    public ResponseEntity<Object> remove(@PathVariable Long userId) {
        log.info("Запрос на удаление пользователя с ID {}", userId);
        return client.remove(userId);
    }
}
