package cz.eman.infinitescroll.model.rest;

public class RestError
{
    private String error;

    public RestError(String error)
    {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
