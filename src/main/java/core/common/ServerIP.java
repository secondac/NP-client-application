package core.common;

public class ServerIP {
    private static String serverAddress;

    public static String getServerAddress() {
        return serverAddress;
    }

    public static void setServerAddress(String address) {
        serverAddress = address;
    }
}