package com.projects.vkotov.todotestapp.presenter;

import android.util.Log;

import com.projects.vkotov.todotestapp.model.Model;
import com.projects.vkotov.todotestapp.model.ModelImpl;
import com.projects.vkotov.todotestapp.model.data.Login;
import com.projects.vkotov.todotestapp.view.IView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;

/**
 * Created by skill on 28.02.2018.
 */

public class LoginPresenter implements BasePresenter {

    private final String TAG = "LoginPresenter";

    private Model model = new ModelImpl();
    private IView view;
    private Disposable dispose = Disposables.empty();

    public LoginPresenter(IView view) {
        this.view = view;
    }

    public void onLoginClick() {
        if (!dispose.isDisposed())
            dispose.dispose();

        model.login(view.getLogin(), view.getPassword())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dispose = d;
                    }

                    @Override
                    public void onNext(Login login) {
                        if (login == null){
                            Log.e(TAG, "response is null");
                            return;
                        }
                        Log.i(TAG, login.getToken());
                        Log.i(TAG, String.valueOf(login.getStatus()));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {}
                });
    }

    @Override
    public void onStop() {
        if (!dispose.isDisposed()) {
            dispose.dispose();
        }
    }
}
