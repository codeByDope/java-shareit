package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDtoForItemWithBookings;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import javax.transaction.Transactional;
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
    private final CommentRepository commentRepository;
    private final RequestRepository requestRepository;
    private final BookingMapper bookingMapper = BookingMapper.INSTANCE;
    private final ItemMapper mapper = ItemMapper.INSTANCE;
    private final CommentMapper commentMapper = CommentMapper.INSTANCE;

    @Transactional
    @Override
    public ItemDto add(ItemDto item, Long ownerId) {
        UserDto owner = userService.getById(ownerId);
        item.setOwner(owner);
        Item res = mapper.toItem(item);

        if (item.getRequestId() != null) {
            ItemRequest itemRequest = requestRepository.findById(item.getRequestId())
                    .orElseThrow(() -> new NoSuchElementException("Запроса с таким ID не найдено"));
            res.setRequest(itemRequest);
        }


        return mapper.toDto(repository.save(res));
    }

    @Transactional
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
    public ItemDtoWithBooking getById(Long itemId, Long userId) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Предмета с таким ID не существует"));
        ItemDtoWithBooking result = mapper.toItemDtoWithBooking(item);

        if (userId.equals(item.getOwner().getId())) {
            LocalDateTime now = LocalDateTime.now();

            BookingDtoForItemWithBookings lastBooking = findLastBooking(itemId, now);
            BookingDtoForItemWithBookings nextBooking = findNextBooking(itemId, now);

            result.setLastBooking(lastBooking);
            result.setNextBooking(nextBooking);
        }

        List<CommentDto> comments = commentMapper.toDtoList(commentRepository.findAllByItem_Id(itemId));

        for (CommentDto comment : comments) {
            comment.setAuthorName(comment.getAuthor().getName());
        }

        if (comments.isEmpty()) {
            result.setComments(new ArrayList<>());
        } else {
            result.setComments(comments);
        }

        return result;
    }


    @Override
    public List<ItemDtoWithBooking> getAllByOwner(Long id) {
        List<Item> items = repository.findAllByOwnerIdOrderByIdAsc(id);
        LocalDateTime now = LocalDateTime.now();
        List<ItemDtoWithBooking> result = new ArrayList<>();

        for (Item item : items) {
            Long itemId = item.getId();
            ItemDtoWithBooking itemDtoWithBooking = mapper.toItemDtoWithBooking(item);

            BookingDtoForItemWithBookings lastBooking = findLastBooking(itemId, now);
            BookingDtoForItemWithBookings nextBooking = findNextBooking(itemId, now);

            itemDtoWithBooking.setLastBooking(lastBooking);
            itemDtoWithBooking.setNextBooking(nextBooking);

            List<CommentDto> comments = commentMapper.toDtoList(commentRepository.findAllByItem_Id(itemId));
            for (CommentDto comment : comments) {
                comment.setAuthorName(comment.getAuthor().getName());
            }

            if (comments.isEmpty()) {
                itemDtoWithBooking.setComments(new ArrayList<>());
            } else {
                itemDtoWithBooking.setComments(comments);
            }

            result.add(itemDtoWithBooking);
        }

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

    @Transactional
    @Override
    public CommentDto addComment(Long itemId, Long userId, CommentDto commentDto) {
        Item item = repository.findById(itemId)
                .orElseThrow(() -> new NoSuchElementException("Предмета с таким ID не существует"));
        UserDto user = userService.getById(userId);
        LocalDateTime now = LocalDateTime.now();

        if (bookingRepository.existsByItem_IdAndBooker_IdAndEndIsBeforeAndStatus(itemId, userId, now, BookingStatus.APPROVED)) {
            commentDto.setItem(mapper.toDto(item));
            commentDto.setAuthor(user);
            commentDto.setCreated(now);

            Comment savedComment = commentRepository.save(commentMapper.fromDto(commentDto));

            CommentDto result = commentMapper.toDto(savedComment);
            result.setAuthorName(user.getName());
            return result;
        } else {
            throw new IllegalStateException("Нельзя оставить комментарий к предмету, который Вы не бронировали");
        }
    }

    private BookingDtoForItemWithBookings findLastBooking(Long itemId, LocalDateTime now) {
        List<Booking> lastBookings = bookingRepository.findAllByItem_IdAndStartIsBeforeOrderByStartDesc(itemId, now);

        if (!lastBookings.isEmpty()) {
            BookingDtoForItemWithBookings result = bookingMapper.toItemWithBookings(lastBookings.get(0));
            BookingStatus status = result.getStatus();

            if (status != BookingStatus.CANCELED && status != BookingStatus.REJECTED) {
                result.setBookerId(result.getBooker().getId());
                return result;
            } else return null;

        }
        return null;
    }

    private BookingDtoForItemWithBookings findNextBooking(Long itemId, LocalDateTime now) {
        List<Booking> nextBookings = bookingRepository.findAllByItem_idAndStartIsAfterOrderByStartAsc(itemId, now);
        if (!nextBookings.isEmpty()) {
            BookingDtoForItemWithBookings result = bookingMapper.toItemWithBookings(nextBookings.get(0));
            BookingStatus status = result.getStatus();

            if (status != BookingStatus.CANCELED && status != BookingStatus.REJECTED) {
                result.setBookerId(result.getBooker().getId());
                return result;
            } else return null;

        }
        return null;
    }

    public List<RequestedItemDto> getAllByRequestId(Long requestId) {
        List<RequestedItemDto> res = mapper.toListRequestedItemDto(repository.findAllByRequestId(requestId));

        return res;
    }
}
