package io.constructor.client.models;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/** Constructor.io Search Response Inner ... uses Gson/Reflection to load data in */
public class SearchResponseInner extends BaseResultsResponse {

    @SerializedName("redirect")
    private Redirect redirect;

    @SerializedName("result_sources")
    private ResultSources resultSources;

    @SerializedName("refined_content")
    private List<RefinedContent> refinedContent;

    /**
     * @return redirect data
     */
    public Redirect getRedirect() {
        return redirect;
    }

    /**
     * @return resultSources data
     */
    public ResultSources getResultSources() {
        return resultSources;
    }

    /**
     * @return refined content
     */
    public List<RefinedContent> getRefinedContent() {
        return refinedContent;
    }

    public void setRedirect(Redirect redirect) {
        this.redirect = redirect;
    }

    public void setResultSources(ResultSources resultSources) {
        this.resultSources = resultSources;
    }

    public void setRefinedContent(List<RefinedContent> refinedContent) {
        this.refinedContent = refinedContent;
    }
}
