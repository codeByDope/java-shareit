package dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDtoForUpdate {
    private Long id;

    private String name;

    private String description;

    private UserDto owner;

    private Boolean available;

    private ItemRequestDto request;
}
