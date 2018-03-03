package com.projects.vkotov.todotestapp.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.other.App;
import com.projects.vkotov.todotestapp.other.di.view.DaggerViewComponent;
import com.projects.vkotov.todotestapp.other.di.view.ViewComponent;
import com.projects.vkotov.todotestapp.other.di.view.ViewDynamicModule;
import com.projects.vkotov.todotestapp.presenter.BasePresenter;
import com.projects.vkotov.todotestapp.presenter.LoginPresenter;
import com.projects.vkotov.todotestapp.view.ActivityCallback;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by skill on 28.02.2018.
 */

public class LoginFragment extends BaseFragment implements LoginView{

    private final String TAG = "LoginFragment";

    @BindView(R.id.login)
    EditText log;
    @BindView(R.id.password)
    EditText pas;
    @BindView(R.id.button)
    Button btn;
    @BindView(R.id.constraint_layout)
    View layout;

    private ViewComponent viewComponent;
    private ActivityCallback activityCallback;

    @OnClick(R.id.button)
    public void loginClick(View view) {
        makeToast("Pressed");
        Log.i(TAG, presenter.toString());
        presenter.onLoginClick();
    }

    @Inject
    LoginPresenter presenter;

    public static LoginFragment newInstance() {
        return  new LoginFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewDynamicModule(new ViewDynamicModule(this, activityCallback))
                    .build();
        }
        viewComponent.inject(this);
        presenter.onCreate(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (ActivityCallback) context;
            Log.i("MessagesFragment", context.toString() + " attached");
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement ActivityCallback");
        }
    }

    @Override
    public void showError(String error) {
        makeToast(error);
    }

    private void makeToast(String text) {
        Snackbar.make(layout, text, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public String getLogin() {
        return log.getText().toString();
    }

    @Override
    public String getPassword() {
        return pas.getText().toString();
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }
}
