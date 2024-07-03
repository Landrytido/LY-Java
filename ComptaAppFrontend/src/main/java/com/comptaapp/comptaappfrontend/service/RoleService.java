package com.comptaapp.comptaappfrontend.service;

import com.comptaapp.comptaappfrontend.model.Role;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RoleService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public List<Role> getAllRoles() throws IOException {
        String url = BASE_URL + "/roles";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            Role[] roles = gson.fromJson(response.body().string(), Role[].class);
            return Arrays.asList(roles);
        }
    }
}