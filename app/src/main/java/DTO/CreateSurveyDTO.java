package DTO;

import com.google.gson.annotations.SerializedName;

public class CreateSurveyDTO {
    @SerializedName("description")
    public String description;
    @SerializedName("name")
    public String name;
    @SerializedName("type")
    public String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
