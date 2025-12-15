package com.cryptostream.report;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class ReportHandler implements RequestHandler<SQSEvent, Void> {

    private final S3Client s3Client = S3Client.create();
    private static final String BUCKET_NAME = System.getenv("REPORT_BUCKET");

    @Override
    public Void handleRequest(SQSEvent event, Context context) {
        for (SQSEvent.SQSMessage msg : event.getRecords()) {
            String userId = msg.getBody();
            context.getLogger().log("Generating report for user: " + userId);

            try {
                byte[] pdfBytes = generatePdf(userId);
                String fileName = "reports/" + userId + "/" + UUID.randomUUID() + ".pdf";

                s3Client.putObject(PutObjectRequest.builder()
                        .bucket(BUCKET_NAME)
                        .key(fileName)
                        .build(), RequestBody.fromBytes(pdfBytes));

                context.getLogger().log("Report uploaded to: " + fileName);

            } catch (Exception e) {
                context.getLogger().log("Error generating report: " + e.getMessage());
            }
        }
        return null;
    }

    private byte[] generatePdf(String userId) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();
        document.add(new Paragraph("CryptoStream Transaction Report"));
        document.add(new Paragraph("User ID: " + userId));
        document.add(new Paragraph("Generated at: " + java.time.LocalDateTime.now()));
        document.add(new Paragraph("------------------------------------------------"));
        document.add(new Paragraph("Transaction History (Simulated)"));
        document.add(new Paragraph("1. BUY BTC - $50,000"));
        document.add(new Paragraph("2. SELL ETH - $3,000"));
        document.close();

        return out.toByteArray();
    }
}
