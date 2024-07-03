package com.comptaapp.comptaappfrontend.util;

import com.comptaapp.comptaappfrontend.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.SimpleStringProperty;
import okhttp3.*;

import java.io.IOException;

public class HttpClientUtil {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(SimpleStringProperty.class, new SimpleStringPropertySerializer())
            .create();
    private static final String BASE_URL = "http://localhost:8080/api";

    public static boolean registerUser(User user) throws IOException {
        String url = BASE_URL + "/register";
        String json = gson.toJson(user);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }

    public static User findUserByUsername(String username) throws IOException {
        String url = BASE_URL + "/users?username=" + username;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return gson.fromJson(response.body().string(), User.class);
        }
    }

    public static String activateUser(Long userId) throws IOException {
        String url = BASE_URL + "/users/activate/" + userId;

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(new byte[0]))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public static boolean verifyUser(String username, String password) throws IOException {
        String url = BASE_URL + "/auth/login";
        String jsonInputString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";

        RequestBody body = RequestBody.create(jsonInputString, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }
}
