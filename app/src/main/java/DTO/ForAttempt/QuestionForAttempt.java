package DTO.ForAttempt;


import com.google.gson.annotations.SerializedName;




import java.util.List;


public class QuestionForAttempt {
    @SerializedName("id")
    private Long id;
    @SerializedName("text")
    private String text;
    @SerializedName("surveyId")
    private long surveyId;
    @SerializedName("answerForAttemptList")
    private List<AnswerForAttempt> answerForAttemptList;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public List<AnswerForAttempt> getAnswerForAttemptList() {
        return answerForAttemptList;
    }

    public void setAnswerForAttemptList(List<AnswerForAttempt> answerForAttemptList) {
        this.answerForAttemptList = answerForAttemptList;
    }
}
