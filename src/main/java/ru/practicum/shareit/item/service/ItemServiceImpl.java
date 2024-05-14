package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForItemWithBookings;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemDtoForUpdate;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private final UserService userService;
    private final ItemRepository repository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper = BookingMapper.INSTANCE;
    private final ItemMapper mapper = ItemMapper.INSTANCE;

    @Override
    public ItemDto add(ItemDto item, Long ownerId) {
        UserDto owner = userService.getById(ownerId);
        item.setOwner(owner);
        return mapper.toDto(repository.save(mapper.toItem(item)));
    }

    @Override
    public ItemDto update(ItemDtoForUpdate updatedItem, Long ownerId) throws AccessDeniedException {
        Long id = updatedItem.getId();

        Item existingItem = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Предмета с таким ID не существует"));

        if (!existingItem.getOwner().getId().equals(ownerId)) {
            throw new AccessDeniedException("Вещь может редактировать только владелец");
        }

        if (updatedItem.getName() != null) {
            existingItem.setName(updatedItem.getName());
        }
        if (updatedItem.getDescription() != null) {
            existingItem.setDescription(updatedItem.getDescription());
        }
        if (updatedItem.getAvailable() != null) {
            existingItem.setAvailable(updatedItem.getAvailable());
        }

        return mapper.toDto(repository.save(existingItem));
    }


    @Override
    public ItemDtoWithBooking getById(Long id, Long userId) {
        Item item = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Предмета с таким ID не существует"));
        ItemDtoWithBooking result = mapper.toItemDtoWithBooking(item);

        if (userId.equals(item.getOwner().getId())) {
            LocalDateTime now = LocalDateTime.now();

            BookingDtoForItemWithBookings lastBooking = findLastBooking(id, now);
            BookingDtoForItemWithBookings nextBooking = findNextBooking(id, now);

            result.setLastBooking(lastBooking);
            result.setNextBooking(nextBooking);
        }

        return result;
    }

    @Override
    public List<ItemDtoWithBooking> getAllByOwner(Long id) {
        List<Item> items = repository.findAllByOwnerIdOrderByIdAsc(id);
        LocalDateTime now = LocalDateTime.now();
        List<ItemDtoWithBooking> result = new ArrayList<>();

        for (Item item : items) {
            ItemDtoWithBooking itemDtoWithBooking = mapper.toItemDtoWithBooking(item);

            BookingDtoForItemWithBookings lastBooking = findLastBooking(item.getId(), now);
            BookingDtoForItemWithBookings nextBooking = findNextBooking(item.getId(), now);

            itemDtoWithBooking.setLastBooking(lastBooking);
            itemDtoWithBooking.setNextBooking(nextBooking);

            result.add(itemDtoWithBooking);
        }
        System.out.println(result);
        return result;
    }

    @Override
    public List<ItemDto> search(String text) {
        if (text.isEmpty()) {
            return new ArrayList<>();
        }
        List<Item> items = repository.search(text);
        return mapper.itemsToItemDto(items);
    }

    private BookingDtoForItemWithBookings findLastBooking(Long itemId, LocalDateTime now) {
        List<Booking> lastBookings = bookingRepository.findAllByItem_IdAndEndIsBeforeOrderByEndAsc(itemId, now);
        if (!lastBookings.isEmpty()) {
            BookingDtoForItemWithBookings result = bookingMapper.toItemWithBookings(lastBookings.get(0));
            result.setBookerId(result.getBooker().getId());
            return result;
        }
        return null;
    }

    private BookingDtoForItemWithBookings findNextBooking(Long itemId, LocalDateTime now) {
        List<Booking> nextBookings = bookingRepository.findAllByItem_idAndStartIsAfterOrderByStartAsc(itemId, now);
        if (!nextBookings.isEmpty()) {
            BookingDtoForItemWithBookings result = bookingMapper.toItemWithBookings(nextBookings.get(0));
            result.setBookerId(result.getBooker().getId());
            return result;
        }
        return null;
    }
}
