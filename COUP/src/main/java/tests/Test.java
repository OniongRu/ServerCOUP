package tests;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import requestObjects.RequestFilters;
import requestObjects.RequestUserObject;
import templater.RequestFiltersDeserializer;
import templater.RequestUserObjectDeserializer;

import javax.servlet.http.HttpServletResponse;

public class Test
{
    public static void main(String[] args) throws Exception
    {
        Test testInstance = new Test();
        testInstance.testGson();
        System.out.println("Geese are cool!");
    }

    public void testGson()
    {
        String gsonTest = "{\n" +
                "  \"groupID\": \"1\",\n" +
                "  \"users\": 92,\n" +
                "  \"dateDelta\": [\n" +
                "    1590440400000,\n" +
                "    1622062799999\n" +
                "  ],\n" +
                "  \"programs\": [\n" +
                "    \"taskhostw.exe\",\n" +
                "    \"java.exe\"\n" +
                "  ],\n" +
                "  \"timeScale\": 4,\n" +
                "  \"tableFilter\": {\n" +
                "    \"tableType\": \"dataByUserProgram\",\n" +
                "    \"title\": \"user\"\n" +
                "  }\n" +
                "}";

        GsonBuilder builderFilters = new GsonBuilder();
        builderFilters.registerTypeAdapter(RequestFilters.class, new RequestFiltersDeserializer());
        RequestFilters filters = null;
        try
        {
            filters = builderFilters.create().fromJson(gsonTest, RequestFilters.class);
        }
        catch (JsonParseException | NullPointerException e )
        {

        }


        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(RequestUserObject.class, new RequestUserObjectDeserializer());
        RequestUserObject user = null;
        try {
            user = builder.create().fromJson(gsonTest, RequestUserObject.class);
        }
        catch (JsonParseException | NullPointerException e )
        {
            System.out.println("meme");
        }
        user.show();
    }
}
