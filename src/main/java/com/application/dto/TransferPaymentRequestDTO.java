package com.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferPaymentRequestDTO {
    private Double availableBalance;
    private Double totalMount;
    private Boolean isEnabled;
}
