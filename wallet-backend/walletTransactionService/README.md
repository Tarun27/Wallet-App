# Wallet Transaction Service

## Overview
Wallet Transaction Service is a microservice designed to handle wallet-based transactions, including credit, debit, transfer, and rollback operations. It leverages Apache Kafka for event-driven communication and is built using Spring Boot.

## Features
- **Credit Transactions**: Add funds to a wallet.
- **Debit Transactions**: Deduct funds from a wallet.
- **Transfer Transactions**: Move funds between wallets.
- **Rollback Transactions**: Reverse failed or erroneous transactions.
- **Kafka Integration**: Uses Kafka for asynchronous event processing and communication between services.
- **RESTful API**: Exposes endpoints for transaction operations.
- **Persistence**: Stores transaction data using JPA repositories.

## Architecture
- **Controller Layer**: Handles HTTP requests (e.g., `TransactionController`).
- **Service Layer**: Business logic for processing transactions (`TransactionService`, `KafkaProducerService`).
- **Kafka Consumers/Producers**: Listens to and publishes events to Kafka topics (`CreditResponseConsumer`, `DebitResponseConsumer`).
- **DTOs**: Data transfer objects for event payloads (`CreditRequestEvent`, `DebitRequestEvent`, etc.).
- **Entities**: JPA entities for transaction data (`WalletTransferTransaction`).
- **Repository Layer**: Interfaces for database operations (`TransactionRepository`).

## Tech Stack
- **Java 17+**
- **Spring Boot**
- **Spring Data JPA**
- **Apache Kafka**
- **Gradle**

## Setup Instructions
1. **Clone the repository**
   ```bash
   git clone <repo-url>
   cd walletTransactionService
   ```
2. **Configure Kafka**
   - Update `src/main/resources/application.yml` or `application.properties` with your Kafka broker details.
   - Use `create-kafka-topics.sh` to create required Kafka topics.
3. **Build the project**
   ```bash
   ./gradlew build
   ```
4. **Run the service**
   ```bash
   ./gradlew bootRun
   ```

## Kafka Topics
- **credit-request**: For credit transaction requests.
- **debit-request**: For debit transaction requests.
- **transfer-initiated**: For transfer initiation events.
- **transaction-status**: For transaction status updates.
- **rollback-event**: For rollback operations.

## API Endpoints
Example endpoints (see `TransactionController.java`):
- `POST /api/transaction/credit` - Initiate a credit transaction
- `POST /api/transaction/debit` - Initiate a debit transaction
- `POST /api/transaction/transfer` - Initiate a transfer between wallets
- `POST /api/transaction/rollback` - Rollback a transaction

## Testing
Run unit and integration tests:
```bash
./gradlew test
```

## Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes
4. Push to the branch
5. Create a pull request

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

---

For more details, refer to the source code and documentation in the repository.

