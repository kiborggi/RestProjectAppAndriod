package DTO;

import com.google.gson.annotations.SerializedName;

public class AnwserTypeValueToCreateDTO {
    @SerializedName("answerID")
    public long answerID;
    @SerializedName("typeID")
    public long typeID;
    @SerializedName("value")
    public float value;

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
}

