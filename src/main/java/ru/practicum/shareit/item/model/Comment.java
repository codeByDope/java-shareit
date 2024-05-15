package ru.practicum.shareit.item.model;

import lombok.*;
import ru.practicum.shareit.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@Builder
@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id", nullable = false)
    Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    User author;

    LocalDateTime created;
}
