package DTO.ForAttempt;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class AttemptDTO {
    @SerializedName("id")
    private Long id;
    @SerializedName("userId")
    private  long userId;
    @SerializedName("surveyId")
    private  long surveyId;
    @SerializedName("created")
    private Date created;
    @SerializedName("ended")
    private Date ended;
    @SerializedName("surveyFotAttempt")
    private SurveyFotAttempt surveyFotAttempt;
    @SerializedName("status")
    private String status;
    @SerializedName("resultText")
    private String resultText;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEnded() {
        return ended;
    }

    public void setEnded(Date ended) {
        this.ended = ended;
    }


    public SurveyFotAttempt getSurveyFotAttempt() {
        return surveyFotAttempt;
    }

    public void setSurveyFotAttempt(SurveyFotAttempt surveyFotAttempt) {
        this.surveyFotAttempt = surveyFotAttempt;
    }

}
