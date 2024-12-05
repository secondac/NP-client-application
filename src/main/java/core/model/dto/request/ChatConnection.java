package core.model.dto.request;

public class ChatConnection {
    private Integer chatId;
    private String username;

    public ChatConnection() {
    }

    public ChatConnection(String username, Integer chatId) {
        this.chatId = chatId;
        this.username = username;
    }

    public Integer getChatId() {
        return chatId;
    }

    public String getUsername() {
        return username;
    }
}