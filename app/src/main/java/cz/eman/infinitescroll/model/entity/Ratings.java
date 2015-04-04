
package cz.eman.infinitescroll.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ratings {

    @SerializedName("critics_rating")
    @Expose
    private String criticsRating;
    @SerializedName("critics_score")
    @Expose
    private Integer criticsScore;
    @SerializedName("audience_score")
    @Expose
    private Integer audienceScore;

    /**
     * 
     * @return
     *     The criticsRating
     */
    public String getCriticsRating() {
        return criticsRating;
    }

    /**
     * 
     * @param criticsRating
     *     The critics_rating
     */
    public void setCriticsRating(String criticsRating) {
        this.criticsRating = criticsRating;
    }

    /**
     * 
     * @return
     *     The criticsScore
     */
    public Integer getCriticsScore() {
        return criticsScore;
    }

    /**
     * 
     * @param criticsScore
     *     The critics_score
     */
    public void setCriticsScore(Integer criticsScore) {
        this.criticsScore = criticsScore;
    }

    /**
     * 
     * @return
     *     The audienceScore
     */
    public Integer getAudienceScore() {
        return audienceScore;
    }

    /**
     * 
     * @param audienceScore
     *     The audience_score
     */
    public void setAudienceScore(Integer audienceScore) {
        this.audienceScore = audienceScore;
    }

}
