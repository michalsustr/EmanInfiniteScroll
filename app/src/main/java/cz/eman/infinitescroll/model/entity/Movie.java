package cz.eman.infinitescroll.model.entity;

import java.util.List;

public class Movie {
    private Integer id;
    private String title;
    private Integer year;
    private String synopsis;
    private List<CastMember> cast;

    public Integer getId() {
        return id;
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
