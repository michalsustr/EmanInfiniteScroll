
package cz.eman.infinitescroll.model.entity;

import com.google.gson.annotations.Expose;

public class LinksList {

    @Expose
    private String self;
    @Expose
    private String next;
    @Expose
    private String prev;
    @Expose
    private String alternate;

    /**
     * 
     * @return
     *     The self
     */
    public String getSelf() {
        return self;
    }

    /**
     * 
     * @param self
     *     The self
     */
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * 
     * @return
     *     The next
     */
    public String getNext() {
        return next;
    }

    /**
     * 
     * @param next
     *     The next
     */
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * 
     * @return
     *     The prev
     */
    public String getPrev() {
        return prev;
    }

    /**
     * 
     * @param prev
     *     The prev
     */
    public void setPrev(String prev) {
        this.prev = prev;
    }

    /**
     * 
     * @return
     *     The alternate
     */
    public String getAlternate() {
        return alternate;
    }

    /**
     * 
     * @param alternate
     *     The alternate
     */
    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

}
