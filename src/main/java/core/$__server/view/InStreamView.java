package core.$__server.view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import core.$__server.common.GsonProvider;
import core.$__server.common.RequestType;
import core.$__server.dto.DTO;
import core.$__server.dto.requestmsg.ChatConnection;
import core.$__server.dto.requestmsg.NewRoom;
import core.$__server.dto.requestmsg.UserLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static core.$__server.common.RequestType.LOGIN;

public class InStreamView<T> {
    private final BufferedReader in;
    private final Gson gson;
    private final Class<T> clazz;

    public InStreamView(Socket socket, Class<T> clazz) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.gson = GsonProvider.getGson();
        this.clazz = clazz;
    }

    public T read() throws IOException, JsonSyntaxException {
        String json = this.in.readLine();
        return gson.fromJson(json, clazz);
    }

    public DTO readDTO() throws IOException, JsonSyntaxException {
        String json = this.in.readLine();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        RequestType requestType = gson.fromJson(jsonObject.get("requestType"), RequestType.class);
        Object requestMsg = null;

        switch (requestType) {
            case NEWROOM -> requestMsg = gson.fromJson(jsonObject.get("requestMsg"), NewRoom.class);
            case CONNECTCHAT -> requestMsg = gson.fromJson(jsonObject.get("requestMsg"), ChatConnection.class);
            case LOGIN -> requestMsg = gson.fromJson(jsonObject.get("requestMsg"), UserLogin.class);
        }
        return new DTO(requestType, requestMsg);
    }
}
