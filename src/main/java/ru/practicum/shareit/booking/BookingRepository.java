package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBooker_IdOrderByStartDesc(Long bookerId);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = ?1 " +
            "AND b.start <= CURRENT_DATE " +
            "AND b.end >= CURRENT_DATE " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByBookerId(Long bookerId);

    List<Booking> findAllByBooker_IdAndEndIsBeforeOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findAllByBooker_IdAndStartIsAfterOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findAllByBooker_IdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    List<Booking> findAllByItem_Owner_IdOrderByStartDesc(Long ownerId);

    @Query("SELECT b FROM Booking b " +
            "JOIN b.item i " +
            "WHERE i.owner.id = ?1 " +
            "AND b.start <= CURRENT_DATE " +
            "AND b.end >= CURRENT_DATE " +
            "ORDER BY b.start DESC")
    List<Booking> findAllCurrentByItemOwnerId(Long ownerId);

    List<Booking> findAllByItem_Owner_IdAndEndIsBeforeOrderByStartDesc(Long ownerId, LocalDateTime now);

    List<Booking> findAllByItem_Owner_IdAndStartIsAfterOrderByStartDesc(Long ownerId, LocalDateTime now);

    List<Booking> findAllByItem_Owner_IdAndStatusOrderByStartDesc(Long ownerId, BookingStatus status);

    List<Booking> findAllByItem_idAndStartIsAfterOrderByStartAsc(Long itemId, LocalDateTime now);
    List<Booking> findAllByItem_IdAndEndIsBeforeOrderByEndAsc(Long itemId, LocalDateTime now);
}
