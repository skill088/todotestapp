package com.projects.vkotov.todotestapp.presenter.vo;

import com.projects.vkotov.todotestapp.model.dto.WithItems;

/**
 * Created by skill on 02.03.2018.
 */

public class TodoList extends WithItems<Todo> {

    private long total;
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
