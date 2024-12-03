package core.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import core.model.Message;

import java.io.IOException;

public class GsonMessageAdapter extends TypeAdapter<Message> {

    @Override
    public void write(JsonWriter out, Message value) throws IOException {
        // JSON 직렬화
        out.beginObject();
        out.name("chatId").value(value.getChatId());
        out.name("userName").value(value.getUsername());
        out.name("message").value(value.getMessage());
        out.endObject();
    }

    @Override
    public Message read(JsonReader in) throws IOException {
        // JSON 역직렬화
        int chatId = 0;
        String userName = null;
        String message = null;

        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            switch (name) {
                case "chatId":
                    chatId = in.nextInt();
                    break;
                case "userName":
                    userName = in.nextString();
                    break;
                case "message":
                    message = in.nextString();
                    break;
                default:
                    in.skipValue(); // 알 수 없는 필드는 건너뜀
            }
        }
        in.endObject();

        return new Message(chatId, userName, message);
    }
}
