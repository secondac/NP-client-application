package core.model.dto.request;

public class UserLogin {
    private String username;

    public UserLogin(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
