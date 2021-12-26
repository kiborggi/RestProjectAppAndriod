package DTO.ForAttempt;


import com.google.gson.annotations.SerializedName;


import java.util.List;


public class SurveyFotAttempt {
    @SerializedName("id")
    private Long id;
    @SerializedName("description")
    private String description;
    @SerializedName("ownerId")
    private long ownerId;
    @SerializedName("name")
    private String name;
    @SerializedName("questionForAttemptList")
    private List<QuestionForAttempt> questionForAttemptList;




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionForAttempt> getQuestionForAttemptList() {
        return questionForAttemptList;
    }

    public void setQuestionForAttemptList(List<QuestionForAttempt> questionForAttemptList) {
        this.questionForAttemptList = questionForAttemptList;
    }
}
