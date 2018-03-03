package com.projects.vkotov.todotestapp.view.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.other.di.view.ViewComponent;
import com.projects.vkotov.todotestapp.presenter.BasePresenter;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * Created by skill on 28.02.2018.
 */

public abstract class BaseFragment extends Fragment {


    protected ViewComponent viewComponent;

    protected abstract BasePresenter getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }
}
