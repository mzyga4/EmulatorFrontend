package pl.mzyga4.emulatorfrontend.util;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JsonParser {
    public static List<?> getListOfTypeFromJson(Context context, Type type, String fileName){
        try{
            Gson gson = new Gson();
            return gson.fromJson(getJsonFromAssets(context, fileName), type);

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
