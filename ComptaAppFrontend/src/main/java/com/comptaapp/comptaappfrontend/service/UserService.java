package com.comptaapp.comptaappfrontend.service;

import com.comptaapp.comptaappfrontend.dto.UserApprovalDto;
import com.comptaapp.comptaappfrontend.dto.UserDTO;
import com.comptaapp.comptaappfrontend.mapper.UserMapper;
import com.comptaapp.comptaappfrontend.model.User;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private static final String BASE_URL = "http://localhost:8080/api";
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public List<User> getAllUsers() throws IOException {
        String url = BASE_URL + "/users";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            UserDTO[] userDTOs = gson.fromJson(response.body().string(), UserDTO[].class);
            return Arrays.stream(userDTOs).map(this::mapToUser).collect(Collectors.toList());
        }
    }

    private User mapToUser(UserDTO userDTO) {
        return UserMapper.toModel(userDTO);
    }

    public boolean registerUser(User user) throws IOException {

        UserDTO userDTO = UserMapper.toDto(user);

        String url = BASE_URL + "/register";
        String json = gson.toJson(userDTO);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.isSuccessful();
        }
    }

    public void approveUser(String username, String roleName) throws IOException {
        UserApprovalDto userApprovalDto = new UserApprovalDto();
        userApprovalDto.setUsername(username);
        userApprovalDto.setRoleName(roleName);

        System.out.println(userApprovalDto);

        String url = BASE_URL + "/users/approve";
        String json = gson.toJson(userApprovalDto);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }
    }

    public void updateUserRole(User user) throws IOException {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setEnabled(user.isEnabled());

        String url = BASE_URL + "/users/updateRole";
        String json = gson.toJson(userDTO);

        RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        }
    }

    public User findUserByUsername(String username) throws IOException {
        String url = BASE_URL + "/users/" + username;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            UserDTO userDTO = gson.fromJson(response.body().string(), UserDTO.class);
            return mapToUser(userDTO);
        }
    }
}
