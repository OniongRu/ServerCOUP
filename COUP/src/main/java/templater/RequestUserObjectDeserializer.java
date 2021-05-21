package templater;

import requestObjects.RequestUserObject;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RequestUserObjectDeserializer implements JsonDeserializer<RequestUserObject> {
    private SimpleDateFormat formatter = new SimpleDateFormat("E MMMM dd yyyy HH:mm:ss", Locale.ENGLISH); //Tue May 11 2021 00:00:00 GMT+0300 (Москва, стандартное время)
    @Override
    public RequestUserObject deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jObject = jsonElement.getAsJsonObject();
        int groupID = jObject.get("groupID").getAsInt();
        int timeScale = jObject.get("timeScale").getAsInt();
        String user = jObject.get("user").getAsString();
        JsonArray jDates = jObject.get("dateDelta").getAsJsonArray();
        Date interval_date_start = null;
        Date interval_date_end = null;
        try {
            System.out.println(jDates.get(0).getAsString());
            interval_date_start = formatter.parse(jDates.get(0).getAsString());
            interval_date_end = formatter.parse(jDates.get(1).getAsString());
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Bad_date_format");
        }
        JsonArray jPrograms = jObject.get("programs").getAsJsonArray();
        ArrayList<String> programs= new ArrayList<>();
        for(int i=0;i<jPrograms.size();i++)
            programs.add(jPrograms.get(i).toString());
        JsonObject jFilters = jObject.get("tableFilter").getAsJsonObject();
        String title = jFilters.get("title").getAsString();
        return new RequestUserObject(groupID, user, timeScale, interval_date_start, interval_date_end, programs, title);
    }
}

//Example
/*
 String gsonTest = "{\n" +
                "  \"groupID\": 1,\n" +
                "  \"user\": \"goose\",\n" +
                "  \"timeScale\": 1,\n" +
                "  \"dateDelta\": [\n" +
                "    \"Tue May 11 2021 00:00:00\",\n" +
                "    \"Tue May 21 2021 14:11:30\"\n" +
                "  ],\n" +
                "  \"programs\": [\n" +
                "    \"dis\",\n" +
                "    \"genshin impact\"\n" +
                "  ],\n" +
                "  \"tableFilter\": {\n" +
                "       \"tableType\": \"dataByUserProgram\",\n" +
                "       \"title\": \"user\"\n" +
                "   }\n" +
                "}";
 */
