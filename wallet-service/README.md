# Wallet Service

The **Wallet Service** is the domain core of the CryptoStream platform. It is responsible for managing user identities, fiat balances (USD), and cryptocurrency portfolios. It ensures transactional integrity for all trade operations.

## 🏗️ Architecture & Design

- **Layered Architecture**: Controller -> Service -> Repository.
- **ACID Transactions**: Uses `@Transactional` to ensure that debits (USD) and credits (Crypto) happen atomically.
- **Asynchronous Communication**: Acts as a **Producer** for the Reporting system, sending messages to AWS SQS when a report is requested.

## 🛠️ Tech Stack & Dependencies

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**: Abstraction for database interactions.
- **PostgreSQL**: Primary data store.
- **Spring Cloud AWS SQS**: For publishing messages to the report queue.
- **Lombok**: Boilerplate reduction.
- **Validation API**: Request validation.

## ⚙️ Configuration

Check `src/main/resources/application.properties` for configuration.

### Key Properties
| Property | Description | Default |
|----------|-------------|---------|
| `spring.datasource.url` | JDBC Connection String | `jdbc:postgresql://postgres:5432/cryptostream` |
| `app.sqs.report-queue` | SQS Queue Name for reports | `report-request-queue` |
| `spring.cloud.aws.region.static` | AWS Region (for SDK) | `us-east-1` |

## 🔌 API Endpoints

### Auth
- `POST /api/auth/login`: Simple login (Demo implementation).

### Wallet
- `GET /api/wallet/{userId}`: Get user details and balance.
- `GET /api/wallet/{userId}/portfolio`: Get list of assets.
- `POST /api/wallet/trade/buy`: Buy crypto asset.
- `POST /api/wallet/trade/sell`: Sell crypto asset.
- `POST /api/wallet/{userId}/report`: Request a PDF report (Async SQS trigger).
