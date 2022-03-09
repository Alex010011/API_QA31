package dto;

//{
//        "email": "string",
//        "password": "string"
//        }

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class AuthRegRequestDto {

    String email;
    String password;
}
