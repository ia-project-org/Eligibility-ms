package org.eligibilityms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientDto {
    private Long clientId;
    private Long clientDetailsId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
