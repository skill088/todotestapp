package com.projects.vkotov.todotestapp.model;

import android.util.Log;

import com.google.gson.stream.MalformedJsonException;
import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.model.api.ApiInterface;
import com.projects.vkotov.todotestapp.model.api.ApiModule;
import com.projects.vkotov.todotestapp.model.dto.ApiResponse;
import com.projects.vkotov.todotestapp.model.dto.LoginDTO;
import com.projects.vkotov.todotestapp.Error;
import com.projects.vkotov.todotestapp.model.dto.TodoListDTO;
import com.projects.vkotov.todotestapp.other.App;

import java.io.UncheckedIOException;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import rx.functions.Action1;

/**
 * Created by skill on 28.02.2018.
 */

public class ModelImpl implements Model {

//    ApiInterface apiInterface = ApiModule.getApiInterface(Constants.BASE_URL);
    @Inject
    protected ApiInterface apiInterface;

    @Inject
    @Named(Constants.UI_THREAD)
    Scheduler uiThread;

    @Inject
    @Named(Constants.IO_THREAD)
    Scheduler ioThread;

    private final ObservableTransformer schedulersTransformer;

    public ModelImpl() {
        App.getComponent().inject(this);
        schedulersTransformer = o -> ((Observable) o).subscribeOn(ioThread)
                .observeOn(uiThread)
                .unsubscribeOn(ioThread);
    }

    @Override
    public Observable<LoginDTO> login(String login, String password) {
        return apiInterface.login(login, password)
                .compose(applySchedulers())
                .compose(statusAsError());
    }

    @Override
    public Observable<TodoListDTO> getTodoList(int page, String token) {
        return apiInterface.getTodoList(page, token)
                .compose(applySchedulers())
                .compose(statusAsError());
    }

    @Override
    public Observable<ApiResponse> signOut(String token) {
        return apiInterface.signOut(token)
                .compose(applySchedulers())
                .compose(statusAsError());
    }

    @SuppressWarnings("unchecked")
    private <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) schedulersTransformer;
    }

    private <T extends ApiResponse> ObservableTransformer<T, T> statusAsError() {
        return observable -> observable.flatMap(response -> {
            if (response != null && response.ok()) {
                Log.d("ResponseStatus:", "response ok");
                return Observable.just(response);
            } else if (response!= null && response.wrongAuth()) {
                return Observable.error(wrongAuth(response));
            } else {
                Log.d("ResponseStatus:", "response error");
                return Observable.error(errorStatusAsError(response));
            }
        })
        .doOnError(Throwable::getMessage);
    }

    private Error wrongAuth(ApiResponse response) {
        return new Error.AuthError(response.getError());
    }

    private Error noItems(ApiResponse response) {
        return new Error.NoItems(response.getError());
    }

    private Error errorStatusAsError(ApiResponse response) {
        Error e;
        if (response == null) {
            e = new Error.NullResponse();
        } else switch(response.getStatus()) {
            case ApiResponse.NO_AUTH: e = new Error.AuthError(response.getError()); break;
            case ApiResponse.NO_ITEMS: e = new Error.NoItems(response.getError()); break;
            default: {
                e = new Error.UnknownStatus(response.getError());
            }
        }
        return e;
    }
}
