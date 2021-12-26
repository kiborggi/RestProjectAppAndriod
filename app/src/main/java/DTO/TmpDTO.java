package DTO;

import com.google.gson.annotations.SerializedName;

public class TmpDTO {

    @SerializedName("text")
    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
