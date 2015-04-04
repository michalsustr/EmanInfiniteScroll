
package cz.eman.infinitescroll.model.entity;

import com.google.gson.annotations.Expose;

public class AlternateIds {

    @Expose
    private String imdb;

    /**
     * 
     * @return
     *     The imdb
     */
    public String getImdb() {
        return imdb;
    }

    /**
     * 
     * @param imdb
     *     The imdb
     */
    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

}
