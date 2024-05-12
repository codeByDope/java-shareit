package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.UserHasAlreadyCreatedException;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper = UserMapper.INSTANCE;
    @Override
    public UserDto add(UserDto user) {
        Long userId = user.getId();
        if (userId != null && repository.getById(userId) != null) {
            throw new UserHasAlreadyCreatedException("Пользователь с таким ID уже существует!");
        } else if (userId != null && userId < 0) {
            throw new IllegalArgumentException("ID пользователя не может быть отрицательным!");
        } else {
            return mapper.toDto(repository.save(mapper.userDtoToUser(user)));
        }
    }

    @Override
    public UserDto update(UserDto user) {
        Long userId = user.getId();
        if (userId == null) {
            throw new IllegalArgumentException("ID пользователя не указан!");
        } else if (userId < 0) {
            throw new IllegalArgumentException("ID пользователя не может быть отрицательным!");
        } else if (repository.getById(userId) == null){
            throw new NoSuchElementException("Юзера с таким ID не существует");
        } else {
            return mapper.toDto(repository.save(mapper.userDtoToUser(user)));
        }
    }


    @Override
    public void remove(Long id) {
        if (repository.getById(id) == null) {
            throw new NoSuchElementException("Юзера с таким ID не существует");
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    public List<UserDto> getAll() {
        return mapper.usersToUserDto(repository.findAll());
    }


    @Override
    public UserDto getById(Long id) {
        if (repository.getById(id) == null) {
            throw new NoSuchElementException("Юзера с таким ID не существует");
        } else {
            return mapper.toDto(repository.getById(id));
        }
    }
}
