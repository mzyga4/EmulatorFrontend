package pl.mzyga4.emulatorfrontend;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonParser {
    private static final Type REVIEW_TYPE = new TypeToken<List<GameSystem>>() {}.getType();

    public static List<GameSystem> getSampleData(Context context){
        try{
            Gson gson = new Gson();
            return gson.fromJson(getJsonFromAssets(context, "gs.json"), REVIEW_TYPE);

        }catch (Exception e){
            return null;
        }
    }

    private static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException e) {
            return null;
        }
        return jsonString;
    }
}
