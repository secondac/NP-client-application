package core.$__testserver;

public class LoginRequestDTO {
    private String username;

    public LoginRequestDTO(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}