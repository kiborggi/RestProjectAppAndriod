package DTO;

import com.google.gson.annotations.SerializedName;

public class AnswerToCreateDTO {

    @SerializedName("questionId")
    public long questionId;
    @SerializedName("text")
    public String text;

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
