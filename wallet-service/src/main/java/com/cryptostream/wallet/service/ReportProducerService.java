package com.cryptostream.wallet.service;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportProducerService {

    private final SqsTemplate sqsTemplate;

    @Value("${app.sqs.report-queue:report-request-queue}")
    private String reportQueue;

    public void requestReport(UUID userId) {
        log.info("Requesting report for user: {}", userId);
        try {
            sqsTemplate.send(reportQueue, userId.toString());
            log.info("Report request sent to SQS for user: {}", userId);
        } catch (Exception e) {
            log.error("Failed to send SQS message: {}", e.getMessage());
            // In a real app, perhaps throw exception or fallback
        }
    }
}
