package com.application.repositories;

import com.application.entities.TransferPaymentEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ITransferPaymentCrudRepository extends CrudRepository<TransferPaymentEntity, String> {

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE TransferPaymentEntity tp SET tp.isProcessed = ?2
                WHERE tp.transactionId = ?1
            """)
    void updateTransactionStatus(String id, Boolean newValor);

    @Modifying
    @Transactional
    @Query(value = """
                UPDATE TransferPaymentEntity tp SET tp.isProcessed = ?2, tp.error = ?3
                WHERE tp.transactionId = ?1
            """)
    void updateTransactionStatusError(String id, Boolean newValor, String error);

}
