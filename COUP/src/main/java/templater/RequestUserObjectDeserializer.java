package templater;

import requestObjects.RequestUserObject;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUserObjectDeserializer implements JsonDeserializer<RequestUserObject>
{
    private Pattern pattern = Pattern.compile("(.*GMT)(.?.?.?)(.?.?)"); //Tue May 11 2021 00:00:00 GMT+0300 (Москва, стандартное время) parses this to string below
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E MMMM dd yyyy HH:mm:ss O", Locale.ENGLISH); //Tue May 11 2021 00:00:00 GMT+0300

    private String parseStringToLocalDateTimeFormatterPattern(String input) throws DateTimeParseException
    {
        Matcher matcher = pattern.matcher(input);
        if (!matcher.find())
        {
            throw new DateTimeParseException("Could not parse date", input, 0);
        }

        return matcher.group(1) + matcher.group(2) + ":" + matcher.group(3);
    }

    @Override
    public RequestUserObject deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException
    {
        JsonObject jObject = jsonElement.getAsJsonObject();
        int groupID = jObject.get("groupID").getAsInt();
        int timeScale = jObject.get("timeScale").getAsInt();
        String user = jObject.get("user").getAsString();

        JsonArray jDates = jObject.get("dateDelta").getAsJsonArray();

        LocalDateTime intervalDateStart = null;
        LocalDateTime intervalDateEnd = null;
        try
        {
            intervalDateStart = LocalDateTime.parse(parseStringToLocalDateTimeFormatterPattern(jDates.get(0).getAsString()), formatter);
            intervalDateEnd = LocalDateTime.parse(parseStringToLocalDateTimeFormatterPattern(jDates.get(1).getAsString()), formatter);
        }
        catch (DateTimeParseException e)
        {
            e.printStackTrace();
            System.out.println("Bad_date_format");
        }

        JsonArray jPrograms = jObject.get("programs").getAsJsonArray();
        ArrayList<String> programs = new ArrayList<>();
        for (int i = 0; i < jPrograms.size(); i++)
        {
            programs.add(jPrograms.get(i).toString());
        }

        JsonObject jFilters = jObject.get("tableFilter").getAsJsonObject();
        String title = jFilters.get("title").getAsString();
        return new RequestUserObject(groupID, user, timeScale, intervalDateStart, intervalDateEnd, programs, title);
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
