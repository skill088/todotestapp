package com.projects.vkotov.todotestapp.model.dto;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by skill on 27.02.2018.
 */

public class ApiResponse {

    public static final int NO_AUTH = 401;
    public static final int NO_ITEMS = 404;

    @SerializedName("status")
    @Expose
    private int status;

    @Nullable
    @SerializedName("error")
    @Expose
    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public boolean ok() {
        return status == 200 || status == 201;
    }

    public boolean wrongAuth()
    {
        return status == NO_AUTH;
    }

    public void setError(@Nullable String error) {
        this.error = error;
    }
}
