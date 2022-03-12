package DTO;

import com.google.gson.annotations.SerializedName;

public class SurveyDTO {
    @SerializedName("id")
    public Long id;
    @SerializedName("description")
    public String description;
    @SerializedName("name")
    public String name;
    @SerializedName("ownerName")
    public String ownerName;
    @SerializedName("numberOfAttempts")
    public long numberOfAttempts;
    @SerializedName("numberOfQuestions")
    public long numberOfQuestions;
    @SerializedName("status")
    public String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public long getNumberOfAttempts() {
        return numberOfAttempts;
    }

    public void setNumberOfAttempts(long numberOfAttempts) {
        this.numberOfAttempts = numberOfAttempts;
    }

    public long getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(long numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
