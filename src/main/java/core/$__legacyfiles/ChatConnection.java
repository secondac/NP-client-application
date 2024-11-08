package core.$__legacyfiles;

public class ChatConnection {
    private Integer chatId;
    private String message;

    public ChatConnection(Integer chatId, String message) {
        this.chatId = chatId;
        this.message = message;
    }

    public Integer getChatId() {
        return chatId;
    }

    public String getMessage() {
        return message;
    }
}