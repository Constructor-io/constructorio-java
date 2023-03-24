package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.Map;

public class RefinedContent {

    @SerializedName("data")
    private Map<String, Object> data;

    /**
     * @return refined content data
     */
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
