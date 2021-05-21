package templater;

import com.google.gson.*;
import requestObjects.RequestFilters;

import java.lang.reflect.Type;

public class RequestFiltersDeserializer implements JsonDeserializer<RequestFilters>{

    @Override
    public RequestFilters deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jObject = jsonElement.getAsJsonObject();
        JsonObject jFilters = jObject.get("tableFilter").getAsJsonObject();
        String title = jFilters.get("title").getAsString();
        return new RequestFilters(title);
    }
}