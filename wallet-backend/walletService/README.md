# Wallet Service

A backend service for managing digital wallets, cards, and transactions. Built with Java and Spring Boot, this service provides RESTful APIs for wallet operations and integrates with Kafka for event-driven communication.

## Features
- Add, view, and manage cards
- Create and manage wallets
- Credit and debit wallet balances
- Kafka integration for asynchronous events
- Docker support for easy deployment

## Technologies Used
- Java 17+
- Spring Boot
- Gradle
- Docker & Docker Compose
- Apache Kafka
- JPA/Hibernate

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle
- Docker (for containerized setup)
- Kafka (local or Docker)

### Build & Run Locally
1. Clone the repository:
   ```bash
   git clone <repo-url>
   cd wallet-backend/walletService
   ```
2. Build the project:
   ```bash
   ./gradlew build
   ```
3. Run the application:
   ```bash
   ./gradlew bootRun
   ```

### Run with Docker
Use Docker Compose to build and start all services:
```bash
docker compose up --build
```
This command will build the Docker images and start all services as defined in `docker-compose.yml`.

## Project Structure
- `controller/` - REST API controllers
- `service/` - Business logic
- `entity/` - JPA entities (Card, Wallet, etc.)
- `repository/` - Spring Data repositories
- `dto/` - Data transfer objects
- `kafka/` - Kafka consumers/producers
- `config/` - Configuration classes

## API Endpoints
- `/cards` - Manage cards (add, list, etc.)
- `/wallet` - Wallet operations (view, create)
- `/wallet/mutation` - Credit/Debit wallet

## Kafka Integration
- Consumers for credit, debit, and user creation events
- Producers for transaction status and rollback events

## Database Configuration
- Default configuration in `application.properties` or `application.yml`
- Supports H2 (in-memory), MySQL, or PostgreSQL

## Testing
Run unit and integration tests:
```bash
./gradlew test
```

## Environment Configuration
- Edit `src/main/resources/application.properties` or `application.yml` for environment-specific settings

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License.
