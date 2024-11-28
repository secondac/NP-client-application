package core.model;

public class Room {
    private Integer id;
    private String name;
    private String host;

    public Room(Integer id, String name, String host) {
        this.id = id;
        this.name = name;
        this.host = host;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }
}