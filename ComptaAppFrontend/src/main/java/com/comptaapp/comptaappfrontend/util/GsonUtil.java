package com.comptaapp.comptaappfrontend.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.SimpleStringProperty;

public class GsonUtil {
    public static Gson getGson() {
        return new GsonBuilder()
                .registerTypeAdapter(SimpleStringProperty.class, new SimpleStringPropertySerializer())
                .create();
    }
}
