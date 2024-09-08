package com.application.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferPaymentDTO {
    private String transactionId;

    private Double availableBalance;

    private Double totalMount;

    private Boolean isEnabled;

    private Boolean isProcessed;

    private String error;
}
