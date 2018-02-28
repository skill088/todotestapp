package com.projects.vkotov.todotestapp.presenter;

import com.projects.vkotov.todotestapp.model.Model;
import com.projects.vkotov.todotestapp.model.ModelImpl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by skill on 28.02.2018.
 */

public abstract class BasePresenter implements IPresenter {

    protected Model dataRepository = new ModelImpl();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}
