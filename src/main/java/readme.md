***
## 클라이언트 작성 개요

### 
### 1. structures
- MVC 패턴으로 작성되었습니다.
- It was written based on the MVC pattern.

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

### 2.