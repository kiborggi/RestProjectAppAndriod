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
    @SerializedName("typeOfQuestion")
    private String typeOfQuestion;
    @SerializedName("numFrom")
    private float numFrom;
    @SerializedName("numTo")
    private float numTo;

    public String getTypeOfQuestion() {
        return typeOfQuestion;
    }

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

    public void setTypeOfQuestion(String typeOfQuestion) {
        this.typeOfQuestion = typeOfQuestion;
    }

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
