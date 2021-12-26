package DTO.ForAttempt;

import com.google.gson.annotations.SerializedName;




public class AnswerForAttempt {
    @SerializedName("id")
    private Long id;

    @SerializedName("questionId")
    private Long questionId;

    @SerializedName("text")
    private String text;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
