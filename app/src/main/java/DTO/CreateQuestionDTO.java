package DTO;

import com.google.gson.annotations.SerializedName;

public class CreateQuestionDTO {
    @SerializedName("surveyId")
    public long surveyId;
    @SerializedName("text")
    public String text;
    @SerializedName("typeOfQuestion")
    public String typeOfQuestion;
    @SerializedName("numFrom")
    private float numFrom;
    @SerializedName("numTo")
    private float numTo;


    public float getNumFrom() {
        return numFrom;
    }

    public void setNumFrom(float numFrom) {
        this.numFrom = numFrom;
    }

    public float getNumTo() {
        return numTo;
    }

    public void setNumTo(float numTo) {
        this.numTo = numTo;
    }

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

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
