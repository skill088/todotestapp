package com.projects.vkotov.todotestapp.presenter;

import com.projects.vkotov.todotestapp.model.Model;
import com.projects.vkotov.todotestapp.model.ModelImpl;
import com.projects.vkotov.todotestapp.other.App;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by skill on 28.02.2018.
 */

public abstract class BasePresenter implements IPresenter {

    @Inject
    protected Model model;

    @Inject
    protected CompositeDisposable compositeDisposable;

    public BasePresenter() {
        App.getComponent().inject(this);
    }

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}
