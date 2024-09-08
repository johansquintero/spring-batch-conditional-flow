package com.application.batch;

import com.application.dto.TransferPaymentDTO;
import com.application.services.ITransferPaymentService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ValidateAccountTasklet implements Tasklet {
    @Autowired
    private ITransferPaymentService transferPaymentService;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        Boolean filterIsApproved = true;
        String transactionId = (String) chunkContext.getStepContext().getJobParameters().get("transactionId");

        TransferPaymentDTO transferPayment = this.transferPaymentService.getById(transactionId).orElseThrow();

        if (!transferPayment.getIsEnabled()){
            filterIsApproved = false;
            chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("message","The account is not enabled!");
        }
        if (transferPayment.getTotalMount()>transferPayment.getAvailableBalance()){
            filterIsApproved = false;
            chunkContext.getStepContext()
                    .getStepExecution()
                    .getJobExecution()
                    .getExecutionContext()
                    .put("message","The total amount to pay is bigger than available balance!");
        }
        ExitStatus exitStatus = null;
        if (filterIsApproved){
            exitStatus = new ExitStatus("VALID");
            stepContribution.setExitStatus(exitStatus);
        }else {
            exitStatus = new ExitStatus("INVALID");
            stepContribution.setExitStatus(exitStatus);
        }
        return RepeatStatus.FINISHED;
    }
}
