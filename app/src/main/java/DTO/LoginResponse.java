package DTO;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("username")
    public String username;
    @SerializedName("token")
    public String token;
}