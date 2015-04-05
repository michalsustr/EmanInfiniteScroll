
package cz.eman.infinitescroll.model.entity;

import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name="movie")
public class Movie extends Model {

    @Expose
    @Column(unique = true)
    @SerializedName("id")
    private Integer sid;
    @Expose
    @Column
    private String title;
    @Expose
    @Column
    private Integer year;
    @SerializedName("mpaa_rating")
    @Expose
    private String mpaaRating;
    @SerializedName("release_dates")
    @Expose
    private ReleaseDates releaseDates;
    @Expose
    private Ratings ratings;
    @Expose
    @Column
    private String synopsis;
    @Expose
    @Column
    private Posters posters;
    @SerializedName("abridged_cast")
    @Expose
    @Column
    private List<AbridgedCast> abridgedCast = new ArrayList<AbridgedCast>();
    @SerializedName("alternate_ids")
    @Expose
    private AlternateIds alternateIds;
    @Expose
    private LinksMovie links;
    @SerializedName("critics_consensus")
    @Expose
    private String criticsConsensus;

    /**
     *
     * @return
     *     The id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     *
     * @param sid
     *     The id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     *
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     *     The year
     */
    public Integer getYear() {
        return year;
    }

    /**
     *
     * @param year
     *     The year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     *
     * @return
     *     The mpaaRating
     */
    public String getMpaaRating() {
        return mpaaRating;
    }

    /**
     *
     * @param mpaaRating
     *     The mpaa_rating
     */
    public void setMpaaRating(String mpaaRating) {
        this.mpaaRating = mpaaRating;
    }

    /**
     *
     * @return
     *     The releaseDates
     */
    public ReleaseDates getReleaseDates() {
        return releaseDates;
    }

    /**
     *
     * @param releaseDates
     *     The release_dates
     */
    public void setReleaseDates(ReleaseDates releaseDates) {
        this.releaseDates = releaseDates;
    }

    /**
     *
     * @return
     *     The ratings
     */
    public Ratings getRatings() {
        return ratings;
    }

    /**
     *
     * @param ratings
     *     The ratings
     */
    public void setRatings(Ratings ratings) {
        this.ratings = ratings;
    }

    /**
     *
     * @return
     *     The synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     *
     * @param synopsis
     *     The synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     *
     * @return
     *     The posters
     */
    public Posters getPosters() {
        return posters;
    }

    /**
     *
     * @param posters
     *     The posters
     */
    public void setPosters(Posters posters) {
        this.posters = posters;
    }

    /**
     *
     * @return
     *     The abridgedCast
     */
    public List<AbridgedCast> getAbridgedCast() {
        if(abridgedCast == null || abridgedCast.size() == 0) {
            abridgedCast = new Select().from(AbridgedCast.class).where("movieId = ?", sid).execute();
        }
        return abridgedCast;
    }

    /**
     *
     * @param abridgedCast
     *     The abridged_cast
     */
    public void setAbridgedCast(List<AbridgedCast> abridgedCast) {
        this.abridgedCast = abridgedCast;
    }

    /**
     *
     * @return
     *     The alternateIds
     */
    public AlternateIds getAlternateIds() {
        return alternateIds;
    }

    /**
     *
     * @param alternateIds
     *     The alternate_ids
     */
    public void setAlternateIds(AlternateIds alternateIds) {
        this.alternateIds = alternateIds;
    }

    /**
     *
     * @return
     *     The links
     */
    public LinksMovie getLinks() {
        return links;
    }

    /**
     *
     * @param links
     *     The links
     */
    public void setLinks(LinksMovie links) {
        this.links = links;
    }

    /**
     *
     * @return
     *     The criticsConsensus
     */
    public String getCriticsConsensus() {
        return criticsConsensus;
    }

    /**
     *
     * @param criticsConsensus
     *     The critics_consensus
     */
    public void setCriticsConsensus(String criticsConsensus) {
        this.criticsConsensus = criticsConsensus;
    }

}