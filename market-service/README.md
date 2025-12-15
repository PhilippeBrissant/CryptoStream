# Market Service

The **Market Service** acts as a real-time proxy for cryptocurrency market data. It decouples the frontend from external rate limits and provides a robust, low-latency stream of price updates.

## 🏗️ Architecture & Design

- **Reactive Programming**: Built on **Spring WebFlux** (Reactor) to handle non-blocking I/O.
- **WebSocket Push**: Maintains persistent connections with clients to push price updates in real-time, eliminating the need for frontend polling.
- **Resilience**: Implements the **Circuit Breaker** pattern using Resilience4j. If the external API (CoinGecko) fails, the service degrades gracefully (e.g., returning cached data).
- **Scheduled Polling**: A background scheduler fetches synchronization data periodically.

## 🛠️ Tech Stack & Dependencies

- **Java 17**
- **Spring Boot 3.2.0 (WebFlux)**
- **Spring WebSocket**: For real-time client communication.
- **Resilience4j**: Circuit breaking and fault tolerance.
- **WebClient**: Non-blocking HTTP client.

## ⚙️ Configuration

### Key Properties (YAML)
```yaml
resilience4j.circuitbreaker:
  instances:
    coingecko:
      slidingWindowSize: 10
      failureRateThreshold: 50
      waitDurationInOpenState: 5s
```

## 🔌 Communication

### WebSocket
- **Endpoint**: `/ws/prices`
- **Protocol**: JSON
- **Structure**:
  ```json
  {
    "bitcoin": { "usd": 50000 },
    "ethereum": { "usd": 3000 },
    "solana": { "usd": 150 }
  }
  ```
