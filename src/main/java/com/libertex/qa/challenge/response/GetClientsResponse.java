package com.libertex.qa.challenge.response;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetClientsResponse {

    private ResultCode resultCode;
    private List<String> clients;

}
