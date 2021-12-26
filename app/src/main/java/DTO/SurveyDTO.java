package DTO;

import com.google.gson.annotations.SerializedName;

public class SurveyDTO {
    @SerializedName("id")
    public Long id;
    @SerializedName("description")
    public String description;
    @SerializedName("name")
    public String name;
    @SerializedName("ownerId")
    public long ownerID;


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

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }
}
