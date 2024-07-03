package com.comptaapp.comptaappfrontend.service;

import com.comptaapp.comptaappfrontend.dto.UserDTO;
import com.comptaapp.comptaappfrontend.mapper.UserMapper;
import com.comptaapp.comptaappfrontend.model.User;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class AuthService {
    private static final String BASE_URL = "http://localhost:8080/api";
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();

    public User authenticate(String username, String password) {
        String url = BASE_URL + "/auth/login";
        UserLoginDto loginDto = new UserLoginDto(username, password);
        String json = gson.toJson(loginDto);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                System.out.println("Response Body: " + responseBody); // Log the response body for debugging

                // Deserialize JSON to UserDto and then map to User
                UserDTO userDto = gson.fromJson(responseBody, UserDTO.class);
                return UserMapper.toModel(userDto);
            } else {
                System.err.println("Request failed: " + response.message());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
