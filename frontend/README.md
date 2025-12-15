# CryptoStream Frontend

The Frontend is a modern **Single Page Application (SPA)** built with **Angular (v16+)**. It provides a reactive, real-time user interface for the CryptoStream platform.

## 🏗️ Architecture & Design

- **State Management (NgRx)**:
    - Uses a Redux-pattern store for maintaining the **Wallet State** (Balance, Assets) and **Auth State**.
    - **Actions/Reducers**: Predictable state transitions.
    - **Selectors**: Efficient data derived from the store.
- **Reactive Programming (RxJS)**:
    - Heavily utilizes Observables.
    - **WebSocketSubject**: Manages the connection to `market-service` with auto-reconnection logic (`retry`).
- **Smart/Dumb Components**:
    - `index.html` (Simplified Container for this Demo) acts as the main view orchestration.

## 🛠️ Tech Stack & Dependencies

- **Angular 16+**
- **NgRx Store**: State management.
- **RxJS**: Reactive extensions.
- **TypeScript**: Typed language foundation.
- **Nginx**: Production server (in Docker).

## 🐳 Docker Deployment

The Docker build uses a **Multi-stage build**:
1.  **Build Stage**: Node container installs dependencies and runs `ng build --prod`.
2.  **Serve Stage**: Nginx container serves the static artifacts and proxies API requests (`/api` -> Wallet Service, `/ws` -> Market Service).

### Nginx Proxy Config
```nginx
location /api/ {
    proxy_pass http://wallet-service:8080;
}
location /ws/ {
    proxy_pass http://market-service:8081;
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "Upgrade";
}
```
