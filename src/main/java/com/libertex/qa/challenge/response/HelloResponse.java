package com.libertex.qa.challenge.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HelloResponse {

    private ResultCode resultCode;
    private String message;

}
