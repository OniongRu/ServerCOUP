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
                "  \"groupID\": 1,\n" +
                "  \"user\": \"goose\",\n" +
                "  \"timeScale\": 1,\n" +
                "  \"dateDelta\": [\n" +
                "    \"Tue May 11 2021 00:00:00 GMT+1100 (Москва, стандартное время)\",\n" +
                "    \"Fri May 21 2021 14:11:30 GMT+0300 (Москва, стандартное время)\"\n" +
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
