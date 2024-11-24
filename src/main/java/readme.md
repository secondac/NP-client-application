***
## 클라이언트 작성 개요

### 
### 1. structures
- MVC 패턴으로 작성되었습니다.
- It was written based on the MVC design pattern.

```aiignore

│ pakage core │

│
├── model
│   ├── User.java
│   ├── Room.java
│   ├── GameState.java
│   └── dto
│       ├── LoginRequestDTO.java
│       ├── RoomRequestDTO.java
│       ├── GameRequestDTO.java
│       └── GameResponseDTO.java
│
├── view
│   ├── LoginView.java
│   ├── RoomListView.java
│   └── GameRoomView.java
│
├── controller
│   ├── LoginController.java
│   ├── RoomListController.java
│   └── GameRoomController.java
│
├── service
│   ├── LoginService.java
│   ├── RoomService.java
│   └── GameService.java
│
└── util
    ├── ?
    └── ?

``` 
### 2. 진행 상황

- 로그인 DB 테스트, JSON 송수신 완료
- 로그인하면 roomlist를 보여주는 걸 구현해야 함
- Create room / enter room 서버 통신 로직 구현해야 함
- 게임 룸 입장 -