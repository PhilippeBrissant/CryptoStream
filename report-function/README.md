# Report Function (AWS Lambda)

This project contains an **AWS Lambda** function written in Java. It is designed to run in a serverless environment, triggered by SQS events, to perform heavy computational tasks (PDF generation) off the main web request thread.

## 🏗️ Architecture & Design

- **Event-Driven**: Triggered solely by messages arriving in the `report-request-queue`.
- **Stateless Execution**: Processes the event, generates a file, uploads it, and terminates.
- **Integration**:
    - **Trigger**: AWS SQS.
    - **Output**: AWS S3 (Bucket storage).

## 🛠️ Tech Stack & Dependencies

- **Java 17**
- **AWS Lambda Java Core**: for `RequestHandler`.
- **AWS Lambda Java Events**: for `SQSEvent` structure.
- **AWS SDK V2 (S3)**: For file upload.
- **OpenPDF**: Library for creating PDF documents programmatically.

## 📦 Deployment Model

The project is built using Maven Shade Plugin to create a "Fat JAR" containing all dependencies, ready for upload to AWS Lambda.

### Handler Class
`com.cryptostream.report.ReportHandler::handleRequest`

### Environment Variables
| Variable | Description |
|----------|-------------|
| `REPORT_BUCKET` | The S3 bucket name where PDFs are stored. |
