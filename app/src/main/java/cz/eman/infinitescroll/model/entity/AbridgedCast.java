
package cz.eman.infinitescroll.model.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table(name="abridged_cast")
public class AbridgedCast extends Model {

    @Expose
    @Column
    private String name;
    @Expose
    @Column
    @SerializedName("id")
    private String sid;

    @Column
    private Integer movieId;

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The id
     */
    public String getSid() {
        return sid;
    }

    /**
     * 
     * @param sid
     *     The id
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
