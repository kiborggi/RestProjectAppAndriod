package DTO.ForAttempt;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.TreeSet;

public class QuestionToSend {
    @SerializedName("questionId")
    private long questionId;
    @SerializedName("listAnswerId")
    private TreeSet<Long> listAnswerId;
    @SerializedName("typeOfQuestion")
    private String typeOfQuestion;
    @SerializedName("numValue")
    private float numValue;

    public float getNumValue() {
        return numValue;
    }

    public void setNumValue(float numValue) {
        this.numValue = numValue;
    }

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public TreeSet<Long> getListAnswerId() {
        return listAnswerId;
    }

    public void setListAnswerId(TreeSet<Long> listAnswerId) {
        this.listAnswerId = listAnswerId;
    }
}
