# CryptoStream Platform

CryptoStream is a comprehensive cryptocurrency portfolio management platform designed to demonstrate advanced software architecture principles, cloud-native development, and real-time data processing.

## 🏗️ Architecture Overview

The project is structured as a **Monorepo** following a **Microservices Architecture**, orchestrated via **Docker** Compose for local development and designed for **AWS** deployment (ECS/Lambda).

### Services
1.  **[Wallet Service](./wallet-service/README.md)**: The core banking ledger. Handles user accounts, USD balances, and asset trading (Buy/Sell) with atomic transactions.
2.  **[Market Service](./market-service/README.md)**: A reactive proxy service. Connects to external Crypto APIs (CoinGecko), implements circuit breakers, and streams real-time prices via WebSockets.
3.  **[Report Function](./report-function/README.md)**: An AWS Lambda function (Java). Triggered asynchronously via SQS to generate PDF transaction reports and upload them to S3.
4.  **[Frontend](./frontend/README.md)**: A Single Page Application (SPA) built with Angular. Features state management via NgRx and connects to the backend for Portfolio management and Real-time price updates.

## 🚀 Quick Start (Local)

### Prerequisites
- Docker & Docker Compose
- Java 17+ (optional, if running outside Docker)
- Node.js 18+ (optional, if running outside Docker)

### Run Everything
```bash
docker-compose up -d --build
```

Access the application at **http://localhost**.

### Default Login
- **Email**: `test@demo.com`
- **Password**: `hashed`

## 🛠️ Infrastructure
The project infrastructure is defined using Terraform (in `/infra`) for AWS resources, including:
- **IAM Roles**: Least Privilege policies for Lambda and ECS.
- **ECS**: Task definitions for container orchestration.
- **SQS/S3**: Queues and Buckets for the reporting flow.

## 🧩 Technologies
- **Java 17 & Spring Boot 3**: Core backend framework.
- **Spring WebFlux**: Reactive stack for Market Service.
- **Angular 16+**: Frontend framework with **NgRx** and **RxJS**.
- **PostgreSQL**: Relational database for the Wallet Service.
- **AWS SQS & Lambda**: Async messaging and serverless compute.
- **Resilience4j**: Fault tolerance library.
- **Docker**: Containerization.
