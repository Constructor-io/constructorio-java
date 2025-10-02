package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;

public class Variant {

    @SerializedName("name")
    private String name;

    @SerializedName("display_name")
    private String displayName;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
