package com.projects.vkotov.todotestapp.presenter.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by skill on 02.03.2018.
 */

public class WithList<T> implements Serializable {

    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
