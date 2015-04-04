
package cz.eman.infinitescroll.model.entity;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class API {

    @Expose
    private Integer total;
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();
    @Expose
    private LinksList links;
    @SerializedName("link_template")
    @Expose
    private String linkTemplate;

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The movies
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * 
     * @param movies
     *     The movies
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     * 
     * @return
     *     The links
     */
    public LinksList getLinks() {
        return links;
    }

    /**
     * 
     * @param links
     *     The links
     */
    public void setLinks(LinksList links) {
        this.links = links;
    }

    /**
     * 
     * @return
     *     The linkTemplate
     */
    public String getLinkTemplate() {
        return linkTemplate;
    }

    /**
     * 
     * @param linkTemplate
     *     The link_template
     */
    public void setLinkTemplate(String linkTemplate) {
        this.linkTemplate = linkTemplate;
    }

}
