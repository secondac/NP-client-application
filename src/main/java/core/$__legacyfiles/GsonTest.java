package core.$__legacyfiles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTest {


    public static void main(String[] args) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson2 = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson);
    }
}
