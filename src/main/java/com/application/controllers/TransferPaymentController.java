package com.application.controllers;

import com.application.dto.TransferPaymentDTO;
import com.application.dto.TransferPaymentRequestDTO;
import com.application.services.ITransferPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/transfers")
@AllArgsConstructor
public class TransferPaymentController {

    private final ITransferPaymentService transferPaymentService;

    private final JobLauncher jobLauncher;
    private final Job job;

    @GetMapping
    public ResponseEntity<List<TransferPaymentDTO>> getAll() {
        return ResponseEntity.ok(this.transferPaymentService.getAll());
    }

    @PostMapping(path = "/transfer")
    public ResponseEntity<?> transferPayment(@RequestBody TransferPaymentRequestDTO transferPaymentRequestDTO) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {

        System.out.println("===========================================\n"+transferPaymentRequestDTO.toString());

        TransferPaymentDTO transaction = this.transferPaymentService.save(transferPaymentRequestDTO).orElseThrow();
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", UUID.randomUUID().toString())
                .addString("transactionId", transaction.getTransactionId()).toJobParameters();

        this.jobLauncher.run(this.job, jobParameters);

        HashMap<String, Object> response = new HashMap<>();
        response.put("transaction", transaction);
        response.put("message", "Transaction received!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
