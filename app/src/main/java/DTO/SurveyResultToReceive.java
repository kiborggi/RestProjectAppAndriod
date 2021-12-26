package DTO;

import com.google.gson.annotations.SerializedName;

public class SurveyResultToReceive {
    @SerializedName("surveyId")
    private  long surveyId;
    @SerializedName("typeId")
    private  long typeId;
    @SerializedName("valueFrom")
    private float valueFrom;
    @SerializedName("valueTo")
    private float valueTo;
    @SerializedName("text")
    private String text;
    @SerializedName("typeText")
    private String typeText;

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public float getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(float valueFrom) {
        this.valueFrom = valueFrom;
    }

    public float getValueTo() {
        return valueTo;
    }

    public void setValueTo(float valueTo) {
        this.valueTo = valueTo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }
}
