package com.projects.vkotov.todotestapp.presenter;

import com.projects.vkotov.todotestapp.presenter.vo.WithList;
import com.projects.vkotov.todotestapp.view.ActivityCallback;
import com.projects.vkotov.todotestapp.view.Page;

import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Function;

/**
 * Created by skill on 02.03.2018.
 */

public abstract class ListPresenter<ModelT extends WithList, DtoT,
        MapperT extends Function<DtoT, ModelT>> extends BasePresenter {

    protected Page<ModelT> page;

    @Nullable
    protected ActivityCallback activityCallback;

    protected ModelT item;

    public ListPresenter(Page<ModelT> page, @Nullable ActivityCallback activityCallback) {
        super();
        this.page = page;
        this.activityCallback = activityCallback;
    }
}
