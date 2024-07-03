package com.comptaapp.comptaappfrontend.util;

import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;

import java.lang.reflect.Type;

public class SimpleStringPropertySerializer implements JsonSerializer<SimpleStringProperty>, JsonDeserializer<SimpleStringProperty> {
    @Override
    public JsonElement serialize(SimpleStringProperty src, Type typeOfSrc, JsonSerializationContext context) {
        // Handle null value
        return src == null || src.get() == null ? JsonNull.INSTANCE : new JsonPrimitive(src.get());
    }

    @Override
    public SimpleStringProperty deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json == null || json.isJsonNull() ? new SimpleStringProperty() : new SimpleStringProperty(json.getAsString());
    }
}
