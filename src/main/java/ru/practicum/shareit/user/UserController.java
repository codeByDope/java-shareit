package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;
import ru.practicum.shareit.user.utils.UserApiPathConstants;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(UserApiPathConstants.USERS_PATH)
public class UserController {
    private final UserService service;

    @GetMapping
    public List<UserDto> getAll() {
        return service.getAll();
    }

    @GetMapping(UserApiPathConstants.USER_ID)
    public UserDto getById(@Positive @PathVariable Long userId) {
        return service.getById(userId);
    }

    @PostMapping
    public UserDto add(@Valid @RequestBody UserDto userDto) {
        return service.add(userDto);
    }

    @PatchMapping(UserApiPathConstants.USER_ID)
    public UserDto update(@Valid @RequestBody UserDto userDto, @Positive @PathVariable Long userId) {
        userDto.setId(userId);
        return service.update(userDto);
    }

    @DeleteMapping(UserApiPathConstants.USER_ID)
    public void remove(@PathVariable Long userId) {
        service.remove(userId);
    }
}
