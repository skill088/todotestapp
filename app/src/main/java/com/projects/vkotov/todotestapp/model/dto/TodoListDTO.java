package com.projects.vkotov.todotestapp.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by skill on 02.03.2018.
 */

public class TodoListDTO extends WithItems<TodoDTO> {
    @SerializedName("total")
    @Expose
    private long total;

    @SerializedName("is_last_page")
    @Expose
    private int isLastPage;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(int isLastPage) {
        this.isLastPage = isLastPage;
    }
}

