package cz.eman.infinitescroll.model.rest;

import cz.eman.infinitescroll.model.entity.RestError;
import retrofit.Callback;
import retrofit.RetrofitError;

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