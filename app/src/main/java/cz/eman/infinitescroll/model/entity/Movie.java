package cz.eman.infinitescroll.model.entity;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Table(name="movie")
public class Movie  extends Model {
    @Column
    @SerializedName("id")
    private Integer sid;
    @Column
    private String title;
    @Column
    private Integer year;
    @Column
    private String synopsis;

    @SerializedName("abridged_cast")
    @Column
    private List<CastMember> cast;

    public Integer getSid() {
        return sid;
    }

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public List<CastMember> getCast() {
        return cast;
    }
}
