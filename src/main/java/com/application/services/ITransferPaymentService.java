package com.application.services;

import com.application.dto.TransferPaymentDTO;
import com.application.dto.TransferPaymentRequestDTO;
import com.application.entities.TransferPaymentEntity;

import java.util.List;
import java.util.Optional;

public interface ITransferPaymentService {
    List<TransferPaymentDTO> getAll();

    Optional<TransferPaymentDTO> save(TransferPaymentRequestDTO transferPaymentRequestDTO);

    Optional<TransferPaymentDTO> getById(String id);

    Optional<TransferPaymentDTO> update(TransferPaymentDTO transferPaymentEntity);

    String delete(String id);

    void updateTransactionalStatus(String id, boolean newValor);
    void updateTransactionalStatusError(String id, boolean newValor,String error);
}
