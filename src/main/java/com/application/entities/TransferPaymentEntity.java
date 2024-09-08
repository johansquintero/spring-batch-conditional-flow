package com.application.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfer_payments")
public class TransferPaymentEntity {

    @Id
    @Column(name = "transaction_id",nullable = false)
    private String transactionId;

    @Column(name = "available_balance",nullable = false)
    private Double availableBalance;

    @Column(name = "total_mount",nullable = false)
    private Double totalMount;

    @Column(name = "is_enabled",nullable = false)
    private Boolean isEnabled;

    @Column(name = "is_processed",nullable = false)
    private Boolean isProcessed;

    private String error;
}
