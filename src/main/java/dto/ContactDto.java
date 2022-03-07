package dto;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class ContactDto {
    String address;
    String description;
    String email;
    int id;
    String lastName;
    String name;
    String phone;

}
