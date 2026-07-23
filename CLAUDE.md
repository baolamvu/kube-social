# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Kube Social is a **group-centric** mobile social network (no personal user feed/wall — all content is owned and distributed inside or between Groups). Business rules, glossary, and dev conventions are pre-written in Vietnamese under `claude/` and `documents/`; read those before implementing any feature-level logic:

- `claude/01_kube_social_context.md` — core entities, roles, governance/voting, privacy modes (source of truth for business rules)
- `claude/02_kube_social_glossary.md` — term definitions with technical notes
- `claude/03_kube_social_rules.md` — architecture/tech stack and DevOps conventions
- `documents/01_technical_architecture.md` — stack + Mermaid diagrams (high-level architecture, CI/CD flow, realtime pubsub flow)
- `documents/02_database_schema.md` — full ERD and table definitions (PostgreSQL)
- `documents/03_backend_structure.md` — **target** package layout, service-layer conventions, and API endpoint table
- `documents/04_user_flows.md`, `documents/05_brd_trd_large_features.md` — user flows and feature specs

**Important:** `documents/03_backend_structure.md` describes the *target* package structure (modules like `user/`, `group/`, `feed/`, `story/`, `vote/`, `connection/`, `chat/`). The actual backend is currently just a fresh Spring Initializr skeleton — only `KubeSocialApplication.java` exists under `backend/src/main/java/com/kubesocial/`. When implementing a module, follow the target structure in that doc rather than assuming it already exists.

## Core Domain Rules (non-obvious, easy to get wrong)

- Groups have three privacy modes: `PRIVATE` (default, members only), `PUBLIC` (visible to "Group Quen" / connected friend groups), and `ONLY_PRIVACY` (invisible to everyone except members/connected groups — no search, friend-request link only visible to members).
- Feed content visibility can be set per-post (`PRIVATE`/`PUBLIC`/`EXCEPT`) and mass-changed later via `PATCH /api/feed/mass-visibility`.
- Admins have broad moderation power but **cannot** delete another member's feed/story content unless that member has left the group.
- Members can only delete their own feed/story content, but can open a vote to temporarily mute *anyone including the Admin* — passes at >50% agree.
- Group-to-group connections ("Group Quen") require the receiving group to hold an internal vote reaching **≥80%** agreement.
- `RoleGuardService` (membership module) is meant to be the single shared gate for ADMIN/MEMBER permission checks — inject it into Feed/Story/Vote services rather than re-implementing role checks per module.
- `VoteCalculationService` is intentionally separate from `VoteService` (percent calculation, threshold comparison, Redis publish) so it can be unit tested in isolation.
- Story TTL (24h) and realtime vote percentages are expected to be backed by Redis (TTL keys / pub-sub), not just DB queries.

## Build & Run

Backend is Java 21, Spring Boot (Maven, wrapper included). Run all Maven commands from `backend/`.

```bash
cd backend

./mvnw compile                 # compile
./mvnw test                    # run all tests
./mvnw test -Dtest=ClassName#methodName   # run a single test
./mvnw spring-boot:run          # run locally (needs local Postgres/Redis + env vars)
./mvnw checkstyle:check          # lint (rules in backend/checkstyle.xml: max line 120, max method length 80, no unused imports)
```

Local/staging environment (Postgres + Redis + backend with hot reload) is Docker Compose based:

```bash
docker compose -f infra/docker-compose.yml -f infra/docker-compose.dev.yml up
```

Copy `.env.example` to `.env` and set `DB_USER`, `DB_PASSWORD`, `REDIS_PASSWORD`, `JWT_SECRET` before running. `application-docker.yml` reads DB/Redis connection info from these env vars; `application.yml` has `flyway.enabled: true` and `jpa.ddl-auto: validate` — schema changes must go through Flyway migrations under `src/main/resources/db/migration/`, not Hibernate auto-DDL.

## Conventions

- Java/Spring backend only for now; frontend (Flutter or React Native, not yet decided/scaffolded) and infra CI/CD are designed in `documents/`/`claude/` but not yet implemented in this repo.
- Any infrastructure/CI-CD diagrams added to docs must use Mermaid.js syntax (per `claude/03_kube_social_rules.md`).
- Keep environment variables and hardcoded values clearly separated in Dockerfiles, docker-compose files, and GitHub workflow files.
- When claude in plan mode, only plan and do not take any actions.
- If better struture could be applied, modify files in documents folder if needed. It's for better excecution in the future.
