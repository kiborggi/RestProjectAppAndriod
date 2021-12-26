package DTO;

import com.google.gson.annotations.SerializedName;

public class TypeDTO {
    @SerializedName("id")
    public Long id;
    @SerializedName("text")
    public String text;
    @SerializedName("surveyId")
    public long surveyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }
}
