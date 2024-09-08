package com.application.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

@Slf4j
public class SendNotificationTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        String transactionId = chunkContext.getStepContext().getJobParameters().get("transactionId").toString();
        log.info("---->There has been sent a notification to the use about the transaction: ".concat(transactionId));

        return RepeatStatus.FINISHED;
    }
}
