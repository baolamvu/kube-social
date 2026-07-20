# Kube Social — Backend Structure (Spring Boot)

## 1. Folder/Package Structure
```
backend/
├── src/main/java/com/kubesocial/
│   ├── KubeSocialApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java        # JWT auth
│   │   ├── WebSocketConfig.java       # WebFlux socket routing
│   │   ├── RedisConfig.java           # pub/sub, cache
│   │   └── OpenApiConfig.java
│   ├── common/
│   │   ├── exception/                 # GlobalExceptionHandler, custom exceptions
│   │   ├── dto/                       # BaseResponse, PageResponse
│   │   └── util/
│   ├── modules/
│   │   ├── user/
│   │   │   ├── controller/ UserController.java
│   │   │   ├── service/    UserService.java
│   │   │   ├── repository/ UserRepository.java
│   │   │   ├── entity/     User.java
│   │   │   └── dto/
│   │   ├── group/
│   │   │   ├── controller/ GroupController.java, GroupProfileController.java
│   │   │   ├── service/    GroupService.java, PrivacyModeService.java
│   │   │   ├── repository/ GroupRepository.java
│   │   │   └── entity/     Group.java
│   │   ├── membership/
│   │   │   ├── controller/ MemberController.java
│   │   │   ├── service/    MemberService.java, RoleGuardService.java   # check ADMIN/MEMBER quyền
│   │   │   ├── repository/ GroupMemberRepository.java
│   │   │   └── entity/     GroupMember.java
│   │   ├── feed/
│   │   │   ├── controller/ FeedController.java
│   │   │   ├── service/    FeedService.java, FeedVisibilityService.java  # xử lý Private/Public/Except
│   │   │   ├── repository/ GroupFeedRepository.java
│   │   │   └── entity/     GroupFeed.java, FeedExceptionGroup.java
│   │   ├── story/
│   │   │   ├── controller/ StoryController.java
│   │   │   ├── service/    StoryService.java, StoryTTLService.java   # cleanup 24h
│   │   │   ├── repository/ GroupStoryRepository.java
│   │   │   └── entity/     GroupStory.java
│   │   ├── vote/
│   │   │   ├── controller/ VoteController.java
│   │   │   ├── service/    VoteService.java, VoteCalculationService.java  # % tính toán realtime
│   │   │   ├── repository/ GroupVoteRepository.java, VoteBallotRepository.java
│   │   │   └── entity/     GroupVote.java, VoteBallot.java
│   │   ├── connection/   # Group Quen
│   │   │   ├── controller/ GroupConnectionController.java
│   │   │   ├── service/    GroupConnectionService.java
│   │   │   ├── repository/ GroupConnectionRepository.java
│   │   │   └── entity/     GroupConnection.java
│   │   ├── chat/
│   │   │   ├── handler/    GroupChatWebSocketHandler.java
│   │   │   ├── service/    ChatService.java, RedisPubSubService.java
│   │   │   ├── repository/ GroupChatMessageRepository.java
│   │   │   └── entity/     GroupChatMessage.java
│   │   └── reaction_comment/
│   │       ├── controller/ ReactionController.java, CommentController.java
│   │       ├── service/
│   │       └── entity/     Reaction.java, Comment.java
│   └── security/
│       ├── JwtProvider.java
│       └── AuthenticationFilter.java
├── src/main/resources/
│   ├── application.yml
│   ├── application-local.yml / application-prod.yml
│   └── db/migration/         # Flyway/Liquibase SQL scripts
├── Dockerfile
├── docker-compose.yml
└── .github/workflows/ci-cd.yml
```

## 2. Nguyên tắc Service Layer
- `RoleGuardService` (module membership) là **cổng kiểm tra quyền chung**, được inject vào Feed/Story/Vote service — tránh viết logic check role rải rác từng module.
- `VoteCalculationService` tách riêng khỏi `VoteService`: chịu trách nhiệm tính %, so threshold, publish kết quả qua Redis — dễ unit test độc lập.
- `FeedVisibilityService` áp dụng rule Private/Public/Except khi query feed cho 1 group xem feed của "group quen".

## 3. API Endpoints (rút gọn)
| Method | Endpoint | Module | Note |
|---|---|---|---|
| POST | /api/auth/register, /login | user | |
| POST | /api/groups | group | tạo group, mặc định PRIVATE |
| PATCH | /api/groups/{id}/privacy-mode | group | đổi PRIVATE/PUBLIC/ONLY_PRIVACY |
| POST | /api/groups/{id}/members/invite | membership | admin only |
| PATCH | /api/groups/{id}/members/{userId}/mute | membership | admin, hoặc vote thắng gọi service này |
| POST | /api/groups/{id}/feed | feed | body gồm visibility_mode, except_group_ids[] |
| PATCH | /api/feed/mass-visibility | feed | mass change mode theo list feed_id |
| POST | /api/groups/{id}/story | story | |
| POST | /api/groups/{id}/votes | vote | vote_type=BAN_MEMBER/GROUP_CONNECTION |
| POST | /api/votes/{id}/ballot | vote | member cast phiếu |
| WS | /ws/votes/{groupId} | vote | realtime % |
| POST | /api/groups/{fromId}/connection-request/{toId} | connection | admin phát lệnh -> tạo vote nội bộ nhóm nhận |
| GET | /api/groups/{id}/friend-request-link | connection | chỉ member ONLY_PRIVACY group thấy |
| WS | /ws/chat/{groupId} | chat | |
