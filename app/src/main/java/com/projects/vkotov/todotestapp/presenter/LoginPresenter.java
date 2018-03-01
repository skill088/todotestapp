package com.projects.vkotov.todotestapp.presenter;

import android.util.Log;

import com.projects.vkotov.todotestapp.model.Model;
import com.projects.vkotov.todotestapp.model.ModelImpl;
import com.projects.vkotov.todotestapp.model.dto.LoginDTO;
import com.projects.vkotov.todotestapp.other.App;
import com.projects.vkotov.todotestapp.presenter.mappers.LoginMapper;
import com.projects.vkotov.todotestapp.view.ActivityCallback;
import com.projects.vkotov.todotestapp.view.fragments.LoginView;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

/**
 * Created by skill on 28.02.2018.
 */

public class LoginPresenter extends BasePresenter {

    private final String TAG = "LoginPresenter";

    @Inject
    LoginMapper mapper;

    private LoginView view;
    private Disposable dispose = Disposables.empty();
    private ActivityCallback activityCallback;

    public LoginPresenter() {
    }

    public LoginPresenter(LoginView view, ActivityCallback activityCallback) {
        this.view = view;
        this.activityCallback = activityCallback;
    }

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void onLoginClick() {
        if (!dispose.isDisposed())
            dispose.dispose();

        model.login(view.getLogin(), view.getPassword())
                .subscribe(new Observer<LoginDTO>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dispose = d;
                    }

                    @Override
                    public void onNext(LoginDTO loginDTO) {
                        if (loginDTO == null){
                            Log.e(TAG, "response is null");
                            return;
                        } /*else if (loginDTO.getStatus() == 401) {
                            view.showError(loginDTO.getError());
                        }*/
                        Log.i(TAG, loginDTO.getToken());
                        Log.i(TAG, String.valueOf(loginDTO.getStatus()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        view.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                });
        addDisposable(dispose);
    }

    public void onCreate(LoginView view) {
        this.view = view;
    }

    @Override
    public void onStop() {
        if (!dispose.isDisposed()) {
            dispose.dispose();
        }
    }
}
