package com.application.services;

import com.application.dto.TransferPaymentDTO;
import com.application.dto.TransferPaymentRequestDTO;
import com.application.entities.TransferPaymentEntity;
import com.application.repositories.ITransferPaymentCrudRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransferPaymentServiceImpl implements ITransferPaymentService {
    private final ITransferPaymentCrudRepository transferPaymentCrudRepository;

    @Override
    public List<TransferPaymentDTO> getAll() {
        List<TransferPaymentEntity> transferPaymentEntityList = (List<TransferPaymentEntity>) this.transferPaymentCrudRepository.findAll();
        return transferPaymentEntityList.stream().map(transferPaymentEntity ->
                TransferPaymentDTO.builder()
                        .error(transferPaymentEntity.getError())
                        .isEnabled(transferPaymentEntity.getIsEnabled())
                        .isProcessed(transferPaymentEntity.getIsProcessed())
                        .totalMount(transferPaymentEntity.getTotalMount())
                        .availableBalance(transferPaymentEntity.getAvailableBalance())
                        .transactionId(transferPaymentEntity.getTransactionId())
                        .build()
        ).toList();
    }

    @Override
    public Optional<TransferPaymentDTO> save(TransferPaymentRequestDTO transferPaymentRequestDTO) {

        String newId = UUID.randomUUID().toString();

        TransferPaymentEntity newTransferPaymentEntity = TransferPaymentEntity
                .builder()
                .availableBalance(transferPaymentRequestDTO.getAvailableBalance())
                .isProcessed(false)
                .isEnabled(transferPaymentRequestDTO.getIsEnabled())
                .totalMount(transferPaymentRequestDTO.getTotalMount())
                .transactionId(newId)
                .build();

        return Optional.of(this.transferPaymentCrudRepository.save(newTransferPaymentEntity)).map(transferPaymentEntity ->
                TransferPaymentDTO.builder()
                        .error(transferPaymentEntity.getError())
                        .isEnabled(transferPaymentEntity.getIsEnabled())
                        .isProcessed(transferPaymentEntity.getIsProcessed())
                        .totalMount(transferPaymentEntity.getTotalMount())
                        .availableBalance(transferPaymentEntity.getAvailableBalance())
                        .transactionId(transferPaymentEntity.getTransactionId())
                        .build());
    }

    @Override
    public Optional<TransferPaymentDTO> getById(String id) {
        return this.transferPaymentCrudRepository.findById(id).map(transferPaymentEntity ->
                TransferPaymentDTO.builder()
                        .error(transferPaymentEntity.getError())
                        .isEnabled(transferPaymentEntity.getIsEnabled())
                        .isProcessed(transferPaymentEntity.getIsProcessed())
                        .totalMount(transferPaymentEntity.getTotalMount())
                        .availableBalance(transferPaymentEntity.getAvailableBalance())
                        .transactionId(transferPaymentEntity.getTransactionId())
                        .build());
    }

    @Override
    public Optional<TransferPaymentDTO> update(TransferPaymentDTO transferPaymentDTO) {
        Optional<TransferPaymentEntity> transferPaymentEntityOpt = this.transferPaymentCrudRepository.findById(transferPaymentDTO.getTransactionId());
        if (transferPaymentEntityOpt.isEmpty()) {
            throw new RuntimeException("La transferencia no existe");
        }
        TransferPaymentEntity newTransferPaymentEntity = transferPaymentEntityOpt.get();
        newTransferPaymentEntity.setError(transferPaymentDTO.getError());
        newTransferPaymentEntity.setIsEnabled(transferPaymentDTO.getIsEnabled());
        newTransferPaymentEntity.setIsProcessed(transferPaymentDTO.getIsProcessed());
        newTransferPaymentEntity.setAvailableBalance(transferPaymentDTO.getAvailableBalance());
        newTransferPaymentEntity.setTotalMount(transferPaymentDTO.getTotalMount());
        return Optional.of(this.transferPaymentCrudRepository.save(newTransferPaymentEntity)).map(
                transferPaymentEntity ->
                        TransferPaymentDTO.builder()
                                .error(transferPaymentEntity.getError())
                                .isEnabled(transferPaymentEntity.getIsEnabled())
                                .isProcessed(transferPaymentEntity.getIsProcessed())
                                .totalMount(transferPaymentEntity.getTotalMount())
                                .availableBalance(transferPaymentEntity.getAvailableBalance())
                                .transactionId(transferPaymentEntity.getTransactionId())
                                .build());

    }

    @Override
    public String delete(String id) {
        Optional<TransferPaymentEntity> transferPaymentEntityOpt = this.transferPaymentCrudRepository.findById(id);
        if (transferPaymentEntityOpt.isEmpty()) {
            throw new RuntimeException("La transferencia no existe");
        }
        this.transferPaymentCrudRepository.deleteById(id);
        return "Transferencia eliminada correctamente";
    }

    @Override
    public void updateTransactionalStatus(String id, boolean newValor) {
        this.transferPaymentCrudRepository.updateTransactionStatus(id, newValor);
    }

    @Override
    public void updateTransactionalStatusError(String id, boolean newValor, String error) {
        this.transferPaymentCrudRepository.updateTransactionStatusError(id, newValor, error);
    }
}
