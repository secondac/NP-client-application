package core.model.dto.response;

public class User {
    private String username;
    private Integer score;

    public User(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', score=" + score + "}";
    }
}

