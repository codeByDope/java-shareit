package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.request.RequestMapper;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestForGetDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository repository;
    private final UserService userService;
    private final ItemService itemService;
    private final RequestMapper mapper = RequestMapper.INSTANCE;

    @Transactional
    @Override
    public ItemRequestDto add(ItemRequestDto requestDto, Long userId) {
        UserDto userDto = userService.getById(userId);
        requestDto.setRequester(userDto);
        requestDto.setCreated(LocalDateTime.now());
        ItemRequest request = mapper.fromDto(requestDto);
        ItemRequestDto res = mapper.toDto(repository.save(request));

        return res;
    }

    @Override
    public List<ItemRequestForGetDto> getByUser(Long userId) {
        List<ItemRequest> requestsByUser = repository.findAllByRequesterIdOrderByCreatedDesc(userId);
        List<ItemRequestForGetDto> requestGetDto = mapper.toListGetDto(requestsByUser);

        for (ItemRequestForGetDto dto : requestGetDto) {
            dto.setItems(itemService.getAllByRequestId(dto.getId()));
        }

        return requestGetDto;
    }

    @Override
    public List<ItemRequestDto> getAll(Long from, Long size) {
        int page = (int) (from / size);
        Pageable pageable = PageRequest.of(page, size.intValue());
        Page<ItemRequest> requests = repository.getAllRequests(pageable);
        return mapper.toListDto(requests.getContent());
    }

    @Override
    public ItemRequestForGetDto getById(Long requestId) {
        ItemRequest request = repository.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Запроса с таким ID не существует"));

        ItemRequestForGetDto res = mapper.toGetDto(request);
        res.setItems(itemService.getAllByRequestId(res.getId()));

        return res;
    }
}
