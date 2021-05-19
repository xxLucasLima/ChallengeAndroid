package com.example.challenge.api;

import android.app.Application;
import android.util.Log;

import com.example.challenge.entities.Login;
import com.example.challenge.entities.ProfileUpdate;
import com.example.challenge.entities.User;
import com.example.challenge.entities.UserHolder;
import com.example.challenge.entities.UserReturnFail;
import com.example.challenge.entities.UsersListHolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserClient implements IUserClient {

    private final Application mApplication;

    public UserClient(Application application) {
        mApplication = application;

    }

    public void logIn(Login login, final IAPIListener listener) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(login);
        String url = "https://dev-uplift-api.herokuapp.com/api/v1/auth/sign_in";

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.println(Log.INFO, "onFailure", e.getStackTrace().toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        UserHolder userHolder = mapper.readValue(response.body().string(), UserHolder.class);
                        userHolder.getUser().setSuccess(true);
                        userHolder.getUser().setUserHeader(response);

                        listener.onLogin(userHolder.getUser());
                    } else {

                        UserReturnFail userFail = mapper.readValue(response.body().string(), UserReturnFail.class);
                        User user = new User();
                        user.setSuccess(userFail.getSuccess());
                        user.setErrors(userFail.getErrors());

                        listener.onLogin(user);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void getAllProfiles(User user, final IAPIListener listener) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        String url = "https://dev-uplift-api.herokuapp.com/api/v1/profiles";
        Request request = new Request.Builder().addHeader("toke-type", user.getTokenType())
                .addHeader("access-token", user.getAccessToken())
                .addHeader("client", user.getClient())
                .addHeader("expiry", user.getExpiry())
                .addHeader("uid", user.getUid())
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.println(Log.INFO, "onFailure", e.getStackTrace().toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                UsersListHolder usersListHolder = mapper.readValue(response.body().string(), UsersListHolder.class);

                listener.onGettingAllProfiles(usersListHolder.getProfiles());
            }
        });

    }

    public void updateProfile(ProfileUpdate profileUpdate, User user, String idProfile, final IAPIListener listener) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(profileUpdate);
        String url = "https://dev-uplift-api.herokuapp.com/api/v1/profiles/" + idProfile;

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .addHeader("toke-type", user.getTokenType())
                .addHeader("access-token", user.getAccessToken())
                .addHeader("client", user.getClient())
                .addHeader("expiry", user.getExpiry())
                .addHeader("uid", user.getUid())
                .url(url)
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.println(Log.INFO, "onFailure", e.getStackTrace().toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    listener.onUpdatingProfile(true);
                } else {

                }

            }
        });

    }

}
