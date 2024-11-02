package core.model;

public class Room {
    private String name;
    private int currentPlayers;

    public Room(String name, int currentPlayers) {
        this.name = name;
        this.currentPlayers = currentPlayers;
    }

    public String getName() {
        return name;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }
}