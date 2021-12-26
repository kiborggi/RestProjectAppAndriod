package DTO;

import com.google.gson.annotations.SerializedName;

public class AnswerDTO {
    @SerializedName("id")
    public long id;
    @SerializedName("questionId")
    public long questionId;
    @SerializedName("text")
    public String text;
    @SerializedName("numberOfTypes")
    public long numberOfTypes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public long getNumberOfTypes() {
        return numberOfTypes;
    }

    public void setNumberOfTypes(long numberOfTypes) {
        this.numberOfTypes = numberOfTypes;
    }
}
