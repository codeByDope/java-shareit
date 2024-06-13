package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestForGetDto;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestMapperTests {

    private RequestMapper requestMapper;

    @BeforeEach
    public void setUp() {
        requestMapper = Mappers.getMapper(RequestMapper.class);
    }

    @Test
    public void testFromDto() {
        UserDto requesterDto = new UserDto(1L, "Requester", "requester@example.com");
        ItemRequestDto itemRequestDto = ItemRequestDto.builder()
                .id(1L)
                .description("Request Description")
                .requester(requesterDto)
                .created(LocalDateTime.now())
                .build();

        ItemRequest itemRequest = requestMapper.fromDto(itemRequestDto);

        assertEquals(itemRequestDto.getId(), itemRequest.getId());
        assertEquals(itemRequestDto.getDescription(), itemRequest.getDescription());
        assertEquals(itemRequestDto.getRequester().getId(), itemRequest.getRequester().getId());
        assertEquals(itemRequestDto.getCreated(), itemRequest.getCreated());
    }

    @Test
    public void testToDto() {
        User requester = User.builder().id(1L).name("Requester").email("requester@example.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Request Description")
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        ItemRequestDto itemRequestDto = requestMapper.toDto(itemRequest);

        assertEquals(itemRequest.getId(), itemRequestDto.getId());
        assertEquals(itemRequest.getDescription(), itemRequestDto.getDescription());
        assertEquals(itemRequest.getRequester().getId(), itemRequestDto.getRequester().getId());
        assertEquals(itemRequest.getCreated(), itemRequestDto.getCreated());
    }

    @Test
    public void testToListDto() {
        User requester = User.builder().id(1L).name("Requester").email("requester@example.com").build();
        ItemRequest itemRequest1 = ItemRequest.builder()
                .id(1L)
                .description("Request Description 1")
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        ItemRequest itemRequest2 = ItemRequest.builder()
                .id(2L)
                .description("Request Description 2")
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        List<ItemRequestDto> itemRequestDtos = requestMapper.toListDto(List.of(itemRequest1, itemRequest2));

        assertThat(itemRequestDtos).hasSize(2);
        assertEquals(itemRequest1.getId(), itemRequestDtos.get(0).getId());
        assertEquals(itemRequest2.getId(), itemRequestDtos.get(1).getId());
    }

    @Test
    public void testToGetDto() {
        User requester = User.builder().id(1L).name("Requester").email("requester@example.com").build();
        ItemRequest itemRequest = ItemRequest.builder()
                .id(1L)
                .description("Request Description")
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        ItemRequestForGetDto itemRequestForGetDto = requestMapper.toGetDto(itemRequest);

        assertEquals(itemRequest.getId(), itemRequestForGetDto.getId());
        assertEquals(itemRequest.getDescription(), itemRequestForGetDto.getDescription());
        assertEquals(itemRequest.getCreated(), itemRequestForGetDto.getCreated());
    }

    @Test
    public void testToListGetDto() {
        User requester = User.builder().id(1L).name("Requester").email("requester@example.com").build();
        ItemRequest itemRequest1 = ItemRequest.builder()
                .id(1L)
                .description("Request Description 1")
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        ItemRequest itemRequest2 = ItemRequest.builder()
                .id(2L)
                .description("Request Description 2")
                .requester(requester)
                .created(LocalDateTime.now())
                .build();

        List<ItemRequestForGetDto> itemRequestForGetDtos = requestMapper.toListGetDto(List.of(itemRequest1, itemRequest2));

        assertThat(itemRequestForGetDtos).hasSize(2);
        assertEquals(itemRequest1.getId(), itemRequestForGetDtos.get(0).getId());
        assertEquals(itemRequest2.getId(), itemRequestForGetDtos.get(1).getId());
    }
}
