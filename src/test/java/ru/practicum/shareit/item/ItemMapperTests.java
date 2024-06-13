package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.dto.RequestedItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemMapperTests {

    private ItemMapper itemMapper;

    @BeforeEach
    public void setUp() {
        itemMapper = Mappers.getMapper(ItemMapper.class);
    }

    @Test
    public void testToDto() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Description")
                .available(true)
                .owner(owner)
                .build();

        ItemDto itemDto = itemMapper.toDto(item);

        assertEquals(item.getId(), itemDto.getId());
        assertEquals(item.getName(), itemDto.getName());
        assertEquals(item.getDescription(), itemDto.getDescription());
        assertEquals(item.getAvailable(), itemDto.getAvailable());
        assertEquals(item.getOwner().getId(), itemDto.getOwner().getId());
    }

    @Test
    public void testToItem() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        ItemDto itemDto = ItemDto.builder()
                .id(1L)
                .name("Test Item")
                .description("Description")
                .available(true)
                .owner(new UserDto(owner.getId(), owner.getName(), owner.getEmail()))
                .build();

        Item item = itemMapper.toItem(itemDto);

        assertEquals(itemDto.getId(), item.getId());
        assertEquals(itemDto.getName(), item.getName());
        assertEquals(itemDto.getDescription(), item.getDescription());
        assertEquals(itemDto.getAvailable(), item.getAvailable());
        assertEquals(itemDto.getOwner().getId(), item.getOwner().getId());
    }

    @Test
    public void testItemsToItemDto() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        Item item1 = Item.builder()
                .id(1L)
                .name("Test Item 1")
                .description("Description 1")
                .available(true)
                .owner(owner)
                .build();

        Item item2 = Item.builder()
                .id(2L)
                .name("Test Item 2")
                .description("Description 2")
                .available(false)
                .owner(owner)
                .build();

        List<ItemDto> itemDtos = itemMapper.itemsToItemDto(List.of(item1, item2));

        assertThat(itemDtos).hasSize(2);
        assertEquals(item1.getId(), itemDtos.get(0).getId());
        assertEquals(item2.getId(), itemDtos.get(1).getId());
    }

    @Test
    public void testItemsToItemsDtoWithBooking() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        Item item1 = Item.builder()
                .id(1L)
                .name("Test Item 1")
                .description("Description 1")
                .available(true)
                .owner(owner)
                .build();

        Item item2 = Item.builder()
                .id(2L)
                .name("Test Item 2")
                .description("Description 2")
                .available(false)
                .owner(owner)
                .build();

        List<ItemDtoWithBooking> itemDtoWithBookings = itemMapper.itemsToItemsDtoWithBooking(List.of(item1, item2));

        assertThat(itemDtoWithBookings).hasSize(2);
        assertEquals(item1.getId(), itemDtoWithBookings.get(0).getId());
        assertEquals(item2.getId(), itemDtoWithBookings.get(1).getId());
    }

    @Test
    public void testToItemDtoWithBooking() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Description")
                .available(true)
                .owner(owner)
                .build();

        ItemDtoWithBooking itemDtoWithBooking = itemMapper.toItemDtoWithBooking(item);

        assertEquals(item.getId(), itemDtoWithBooking.getId());
        assertEquals(item.getName(), itemDtoWithBooking.getName());
        assertEquals(item.getDescription(), itemDtoWithBooking.getDescription());
        assertEquals(item.getAvailable(), itemDtoWithBooking.getAvailable());
        assertEquals(item.getOwner().getId(), itemDtoWithBooking.getOwner().getId());
    }

    @Test
    public void testToItemFromDtoWithBookings() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        ItemDtoWithBooking itemDtoWithBooking = ItemDtoWithBooking.builder()
                .id(1L)
                .name("Test Item")
                .description("Description")
                .available(true)
                .owner(new UserDto(owner.getId(), owner.getName(), owner.getEmail()))
                .build();

        Item item = itemMapper.toItemFromDtoWithBookings(itemDtoWithBooking);

        assertEquals(itemDtoWithBooking.getId(), item.getId());
        assertEquals(itemDtoWithBooking.getName(), item.getName());
        assertEquals(itemDtoWithBooking.getDescription(), item.getDescription());
        assertEquals(itemDtoWithBooking.getAvailable(), item.getAvailable());
        assertEquals(itemDtoWithBooking.getOwner().getId(), item.getOwner().getId());
    }

    @Test
    public void testToRequestedItemDto() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        ItemRequest request = ItemRequest.builder().id(1L).description("Request Description").build();
        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Description")
                .available(true)
                .owner(owner)
                .request(request)
                .build();

        RequestedItemDto requestedItemDto = itemMapper.toRequestedItemDto(item);

        assertEquals(item.getId(), requestedItemDto.getId());
        assertEquals(item.getName(), requestedItemDto.getName());
        assertEquals(item.getDescription(), requestedItemDto.getDescription());
        assertEquals(item.getAvailable(), requestedItemDto.getAvailable());
        assertEquals(item.getRequest().getId(), requestedItemDto.getRequestId());
    }

    @Test
    public void testToListRequestedItemDto() {
        User owner = User.builder().id(1L).name("User").email("user@example.com").build();
        ItemRequest request = ItemRequest.builder().id(1L).description("Request Description").build();
        Item item1 = Item.builder()
                .id(1L)
                .name("Test Item 1")
                .description("Description 1")
                .available(true)
                .owner(owner)
                .request(request)
                .build();

        Item item2 = Item.builder()
                .id(2L)
                .name("Test Item 2")
                .description("Description 2")
                .available(false)
                .owner(owner)
                .request(request)
                .build();

        List<RequestedItemDto> requestedItemDtos = itemMapper.toListRequestedItemDto(List.of(item1, item2));

        assertThat(requestedItemDtos).hasSize(2);
        assertEquals(item1.getId(), requestedItemDtos.get(0).getId());
        assertEquals(item2.getId(), requestedItemDtos.get(1).getId());
    }
}
