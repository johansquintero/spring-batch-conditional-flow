package com.application.batch;

import com.application.services.ITransferPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class CancelTransactionTasklet implements Tasklet {
    @Autowired
    private ITransferPaymentService transferPaymentService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();
        String errorMessage = chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .getString("message");

        log.info("---->>The payment of the transaction {} is cancelling for the next reason: ".concat(errorMessage), transactionId);

        this.transferPaymentService.updateTransactionalStatusError(transactionId, true, errorMessage);


        return RepeatStatus.FINISHED;
    }
}
