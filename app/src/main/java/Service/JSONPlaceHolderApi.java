package Service;

import DTO.LoginReq;
import DTO.LoginResponse;

import DTO.RegisterReq;
import DTO.UserResponse;
import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {

    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginReq login);

    @GET("api/v1/users/me")
    Call<UserResponse> getLoggedUser(@Header("Authorization") String token);

    @POST("api/v1/auth/register")
    Call<LoginResponse> register(@Body RegisterReq registerReq);

}