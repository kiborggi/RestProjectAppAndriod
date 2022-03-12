package Service;

import java.util.List;

import DTO.AnwserTypeValueToCreateDTO;
import DTO.AnswerDTO;
import DTO.AnswerToCreateDTO;
import DTO.AnswerTypeValueDTO;
import DTO.CreateQuestionDTO;
import DTO.CreateSurveyDTO;
import DTO.CreateTypeDTO;
import DTO.ForAttempt.AttemptDTO;
import DTO.ForAttempt.AttemptToSend;
import DTO.LoginReq;
import DTO.LoginResponse;

import DTO.QuestionDTO;
import DTO.RegisterReq;
import DTO.SurveyDTO;
import DTO.SurveyResult;
import DTO.SurveyResultToReceive;
import DTO.TmpDTO;
import DTO.TypeDTO;
import DTO.UserResponse;
import retrofit2.*;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {

    @POST("api/v1/auth/login")
    Call<LoginResponse> login(@Body LoginReq login);

    @GET("api/v1/users/me")
    Call<UserResponse> getLoggedUser(@Header("Authorization") String token);

    @POST("api/v1/auth/register")
    Call<LoginResponse> register(@Body RegisterReq registerReq);

    @GET("api/v1/survey/getAllSurveys")
    Call<List<SurveyDTO>> getAllSurveys(@Header("Authorization") String token);

    @GET("api/v1/survey/getSurvey/{id}")
    Call<SurveyDTO> getSurvey(@Header("Authorization") String token,@Path("id") long id);

    @GET("api/v1/survey/getAllSurveysOfUser")
    Call<List<SurveyDTO>> getAllSurveysOfUser(@Header("Authorization") String token);

    @POST("api/v1/survey/createSurvey")
    Call<SurveyDTO> createSurvey (@Header("Authorization") String token, @Body CreateSurveyDTO createSurveyDTO);

    @GET("api/v1/survey/getQuestionsOfSurvey/{id}")
    Call<List<QuestionDTO>> getQuestionsOfSurvey(@Header("Authorization") String token, @Path("id") long id);

    @GET("api/v1/survey/getTypesOfSurvey/{id}")
    Call<List<TypeDTO>> getTypesOfSurvey(@Header("Authorization") String token, @Path("id") long id);

    @POST("api/v1/survey/createQuestion")
    Call<QuestionDTO> createQuestion(@Header("Authorization") String token, @Body CreateQuestionDTO createQuestionDTO);

    @POST("api/v1/survey/createTypeForSurvey")
    Call<TypeDTO> createTypeForSurvey(@Header("Authorization") String token, @Body CreateTypeDTO createTypeDTO);

    @GET("/api/v1/survey/getAnswersOfQuestion/{id}")
    Call<List<AnswerDTO>> getAnswersOfQuestion(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/v1/survey/createAnswerForQuestion")
    Call<AnswerDTO> createAnswerForQuestion(@Header("Authorization") String token, @Body AnswerToCreateDTO answerToCreateDTO);

    @GET("/api/v1/survey/getAnswerTypeValueOfAnswer/{id}")
    Call<List<AnswerTypeValueDTO>> getAnswerTypeValueOfAnswer(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/v1/survey/createAnswerTypeValue")
    Call<AnswerTypeValueDTO> createAnswerTypeValue(@Header("Authorization") String token, @Body AnwserTypeValueToCreateDTO anserTypeValueToCreateDTO);

    @GET("/api/v1/survey/startAttemptForSurvey/{id}")
    Call<AttemptDTO> startAttemptForSurvey(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/v1/survey/addSurveyResult")
    Call<SurveyResult> addSurveyResult(@Header("Authorization") String token, @Body SurveyResult surveyResult);

    @GET("/api/v1/survey/getResultsOfSurvey/{id}")
    Call<List<SurveyResultToReceive>> getResultsOfSurvey(@Header("Authorization") String token, @Path("id") long id);

    @GET("/api/v1/survey/getAttemptsOfSurvey/{id}")
    Call<List<AttemptDTO>> getAttemptsOfSurvey(@Header("Authorization") String token, @Path("id") long id);

    @POST("/api/v1/survey/receiveAttemptResults")
    Call<TmpDTO> receiveAttemptResults(@Header("Authorization") String token, @Body AttemptToSend atempt);

    @POST("/api/v1/survey/changeSurveyStatus/{id}")
    Call<SurveyDTO> changeSurveyStatus(@Header("Authorization") String token, @Path("id") long id);
}