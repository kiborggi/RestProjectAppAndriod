package DTO;

import com.google.gson.annotations.SerializedName;

public class CreateTypeDTO {

    @SerializedName("surveyId")
    public long surveyId;
    @SerializedName("text")
    public String text;

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
