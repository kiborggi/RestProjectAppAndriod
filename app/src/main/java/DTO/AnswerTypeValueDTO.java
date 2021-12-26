package DTO;

import com.google.gson.annotations.SerializedName;

public class AnswerTypeValueDTO {

    @SerializedName("answerID")
    public long answerID;
    @SerializedName("typeID")
    public long typeID;
    @SerializedName("value")
    public float value;
    @SerializedName("typeText")
    public String typeText;
    @SerializedName("ansewerText")
    public String ansewerText;


    public long getAnswerID() {
        return answerID;
    }

    public void setAnswerID(long answerID) {
        this.answerID = answerID;
    }

    public long getTypeID() {
        return typeID;
    }

    public void setTypeID(long typeID) {
        this.typeID = typeID;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText;
    }

    public String getAnsewerText() {
        return ansewerText;
    }

    public void setAnsewerText(String ansewerText) {
        this.ansewerText = ansewerText;
    }
}
