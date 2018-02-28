package com.projects.vkotov.todotestapp.model;

import com.projects.vkotov.todotestapp.model.api.ApiInterface;
import com.projects.vkotov.todotestapp.model.api.ApiModule;
import com.projects.vkotov.todotestapp.model.data.Login;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by skill on 28.02.2018.
 */

public class ModelImpl implements Model {

    ApiInterface apiInterface = ApiModule.getApiInterface();
    private final ObservableTransformer schedulersTransformer;

    public ModelImpl() {
        schedulersTransformer = o -> ((Observable) o).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Login> login(String login, String password) {
        return apiInterface.login(login, password)
                .compose(applySchedulers());
    }

    @SuppressWarnings("unchecked")
    private <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }
}
