package com.projects.vkotov.todotestapp.model.data;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by skill on 27.02.2018.
 */

public class ApiResponse {

    @SerializedName("status")
    @Expose
    private int status;

    @Nullable
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
}
