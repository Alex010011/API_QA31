package dto;


import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

public class ErrorDto {

//    "code": 0,
//            "details": "string",
//            "message": "string",
//            "timestamp": "2022-03-07T16:09:49.108Z"

    int code;
    String details;
    String message;

}
