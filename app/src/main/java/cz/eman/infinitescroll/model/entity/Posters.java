
package cz.eman.infinitescroll.model.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

@Table(name="posters")
public class Posters extends Model {

    @Expose
    @Column
    private String thumbnail;
    @Expose
    private String profile;
    @Expose
    private String detailed;
    @Expose
    private String original;

    /**
     * 
     * @return
     *     The thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * 
     * @param thumbnail
     *     The thumbnail
     */
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * 
     * @return
     *     The profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * 
     * @param profile
     *     The profile
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * 
     * @return
     *     The detailed
     */
    public String getDetailed() {
        return detailed;
    }

    /**
     * 
     * @param detailed
     *     The detailed
     */
    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }

    /**
     * 
     * @return
     *     The original
     */
    public String getOriginal() {
        return original;
    }

    /**
     * 
     * @param original
     *     The original
     */
    public void setOriginal(String original) {
        this.original = original;
    }

}
