package core.model.dto;

public class LoginRequestDTO {
    private String username;
    private String serverIp;

    public LoginRequestDTO(String username, String serverIp) {
        this.username = username;
        this.serverIp = serverIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }
}