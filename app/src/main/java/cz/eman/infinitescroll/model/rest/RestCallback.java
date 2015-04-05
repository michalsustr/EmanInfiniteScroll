package cz.eman.infinitescroll.model.rest;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Custom rest callback that handles simple error states.
 * @param <T>
 */
public abstract class RestCallback<T> implements Callback<T>
{
    public abstract void failure(RestError restError);

    @Override
    public void failure(RetrofitError error)
    {
        RestError restError = (RestError) error.getBodyAs(RestError.class);

        if (restError != null)
            failure(restError);
        else
        {
            failure(new RestError(error.getMessage()));
        }
    }
}