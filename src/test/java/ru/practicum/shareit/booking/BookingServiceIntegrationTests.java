package ru.practicum.shareit.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDtoForAnswer;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.booking.service.BookingServiceImpl;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;

import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class BookingServiceIntegrationTests {

    @Autowired
    private BookingServiceImpl bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private User owner;
    private User booker;
    private Item item;
    private Booking booking;

    @BeforeEach
    public void setUp() {
        owner = User.builder()
                .name("Owner")
                .email("owner@example.com")
                .build();
        owner = userRepository.save(owner);

        booker = User.builder()
                .name("Booker")
                .email("booker@example.com")
                .build();
        booker = userRepository.save(booker);

        item = Item.builder()
                .name("Test Item")
                .description("Description of test item")
                .owner(owner)
                .available(true)
                .build();
        item = itemRepository.save(item);

        booking = Booking.builder()
                .item(item)
                .booker(booker)
                .start(LocalDateTime.now().plusDays(1))
                .end(LocalDateTime.now().plusDays(2))
                .status(BookingStatus.WAITING)
                .build();
        booking = bookingRepository.save(booking);
    }

    @Test
    public void testApproveBooking() throws Exception {
        Long bookingId = booking.getId();
        Long ownerId = owner.getId();

        BookingDtoForAnswer approvedBooking = bookingService.approve(bookingId, true, ownerId);

        assertThat(approvedBooking.getStatus()).isEqualTo(BookingStatus.APPROVED);
        assertThat(approvedBooking.getId()).isEqualTo(bookingId);
    }

    @Test
    public void testRejectBooking() throws Exception {
        Long bookingId = booking.getId();
        Long ownerId = owner.getId();

        BookingDtoForAnswer rejectedBooking = bookingService.approve(bookingId, false, ownerId);

        assertThat(rejectedBooking.getStatus()).isEqualTo(BookingStatus.REJECTED);
        assertThat(rejectedBooking.getId()).isEqualTo(bookingId);
    }

    @Test
    public void testApproveBookingByNonOwnerThrowsException() {
        Long bookingId = booking.getId();
        Long bookerId = booker.getId();

        assertThrows(AccessDeniedException.class, () -> {
            bookingService.approve(bookingId, true, bookerId);
        });
    }

    @Test
    public void testApproveAlreadyApprovedBookingThrowsException() throws Exception {
        Long bookingId = booking.getId();
        Long ownerId = owner.getId();

        bookingService.approve(bookingId, true, ownerId);

        assertThrows(ValidationException.class, () -> {
            bookingService.approve(bookingId, true, ownerId);
        });
    }

    @Test
    public void testApproveNonExistentBookingThrowsException() {
        Long nonExistentBookingId = 999L;
        Long ownerId = owner.getId();

        assertThrows(NoSuchElementException.class, () -> {
            bookingService.approve(nonExistentBookingId, true, ownerId);
        });
    }
}

