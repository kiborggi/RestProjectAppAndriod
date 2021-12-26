package DTO.ForAttempt;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AttemptToSend {
    @SerializedName("attemptId")
    private long attemptId;
    @SerializedName("surveyId")
    private long surveyId;
    @SerializedName("listQuestion")
    private List<QuestionToSend> listQuestion;


    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(long attemptId) {
        this.attemptId = attemptId;
    }

    public List<QuestionToSend> getListQuestion() {
        return listQuestion;
    }

    public void setListQuestion(List<QuestionToSend> listQuestion) {
        this.listQuestion = listQuestion;
    }
}
