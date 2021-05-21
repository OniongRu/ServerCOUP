package templater;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RequestMapGenerator {

    public static Map<String, String> entranceData(String data)
    {
        Gson gson = new Gson();
        Type typeLogPwd = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> mapReturn = gson.fromJson(data, typeLogPwd);
        return mapReturn;
    }
}
