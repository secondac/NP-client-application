package core.model;

import com.google.gson.annotations.Expose;

/**
 * Message transfer object for chatting mechanism
 */
public class Message {

    @Expose
    private Integer chatId;
    @Expose
    private String username;
    @Expose
    private String message;

    public Message() {
    }

    public Message(Integer chatId, String username, String message) {
        this.chatId = chatId;
        this.username = username;
        this.message = message;
    }

    public Integer getChatId() {
        return chatId;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "chatId=" + chatId +
                ", username='" + username + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}