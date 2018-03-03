package com.projects.vkotov.todotestapp.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by skill on 02.03.2018.
 */

public class WithItems<T> extends ApiResponse {

    @SerializedName("items")
    @Expose
    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
