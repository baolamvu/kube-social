# Kube Social — Technical Architecture

## 1. Stack
| Layer | Công nghệ |
|---|---|
| Frontend | Flutter / React Native (cross-platform) |
| Backend | Java 17+, Spring Boot, Spring WebFlux (WS realtime) |
| DB | PostgreSQL (chính), Redis (cache/session/pub-sub realtime) |
| Container | Docker (multi-stage build) |
| CI/CD | GitHub Actions |
| Infra | Docker Compose (local/staging), VPS/Cloud SSH deploy (prod v1) |

## 2. High-level Architecture

```mermaid
graph TB
    subgraph Client
        A[Flutter/RN App]
    end
    subgraph Backend["Spring Boot Backend"]
        GW[API Gateway / Controller Layer]
        SVC[Service Layer - Business Logic]
        WS[WebFlux WebSocket Handler]
    end
    subgraph Data
        PG[(PostgreSQL)]
        RD[(Redis - Cache/Session/PubSub)]
    end

    A -- REST/HTTPS --> GW
    A -- WS --> WS
    GW --> SVC
    WS --> SVC
    SVC --> PG
    SVC --> RD
    WS -. subscribe vote/chat channel .-> RD
```

## 3. Deployment / CI-CD Flow

```mermaid
graph LR
    Dev[Push/Merge -> main] --> Test[Run Unit Test]
    Test --> Build[Build Docker Image - multi-stage]
    Build --> Push[Push to Docker Registry]
    Push --> SSH[SSH vào Server]
    SSH --> Pull[docker pull latest image]
    Pull --> Deploy[Rolling restart container]
```

Quy ước file:
- `Dockerfile` (multi-stage: build jar bằng maven/gradle image -> copy sang JRE-slim image)
- `docker-compose.yml`: services `backend`, `postgres`, `redis` (local/staging)
- `.github/workflows/ci-cd.yml`: job `test` -> `build-push` -> `deploy`
- Biến môi trường (DB_URL, REDIS_URL, JWT_SECRET...) đưa vào GitHub Secrets + `.env` (không hardcode).

## 4. Realtime Mechanism (Vote & Chat)
```mermaid
sequenceDiagram
    participant U as User A (Client)
    participant WS as WS Handler (Node instance)
    participant RD as Redis PubSub
    participant WS2 as WS Handler (Node khác)
    participant U2 as User B (Client)

    U->>WS: Vote / Chat message
    WS->>RD: PUBLISH channel:group:{id}
    RD-->>WS2: message
    WS2-->>U2: push realtime update
```
Lý do dùng Redis PubSub: đảm bảo broadcast đúng khi backend scale nhiều instance (horizontal scaling), không dùng in-memory session list.
