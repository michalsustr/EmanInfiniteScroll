
package cz.eman.infinitescroll.model.entity;

import com.google.gson.annotations.Expose;

public class ReleaseDates {

    @Expose
    private String theater;

    /**
     * 
     * @return
     *     The theater
     */
    public String getTheater() {
        return theater;
    }

    /**
     * 
     * @param theater
     *     The theater
     */
    public void setTheater(String theater) {
        this.theater = theater;
    }

}
