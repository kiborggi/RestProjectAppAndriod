package DTO;

import com.google.gson.annotations.SerializedName;

public class RegisterReq {
    @SerializedName("username")
    public String username;
    @SerializedName("password")
    public String password;
    @SerializedName("firstName")
    public String firstName;
    @SerializedName("lastName")
    public String lastName;
    @SerializedName("email")
    public String email;

}
