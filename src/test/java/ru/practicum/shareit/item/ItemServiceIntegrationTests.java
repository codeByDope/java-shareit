package ru.practicum.shareit.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDtoWithBooking;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.item.service.ItemServiceImpl;
import ru.practicum.shareit.request.RequestRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class ItemServiceIntegrationTests {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;
    private Item item;
    private Booking booking;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .name("Owner")
                .email("owner@example.com")
                .build();

        user = userRepository.save(user);

        item = Item.builder()
                .name("Test Item")
                .description("Description of test item")
                .owner(user)
                .available(true)
                .build();

        item = itemRepository.save(item);

        booking = Booking.builder()
                .item(item)
                .booker(user)
                .start(LocalDateTime.now().minusDays(1))
                .end(LocalDateTime.now().plusDays(1))
                .status(BookingStatus.APPROVED)
                .build();

        booking = bookingRepository.save(booking);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    public void testGetAllByOwner() {
        List<ItemDtoWithBooking> items = itemService.getAllByOwner(user.getId(), 0L, 10L);

        assertThat(items).isNotEmpty();
        assertThat(items.get(0).getId()).isEqualTo(item.getId());
        assertThat(items.get(0).getLastBooking()).isNotNull();
        assertThat(items.get(0).getNextBooking()).isNull();
    }
}