package com.projects.vkotov.todotestapp.view.fragments;

import android.support.v4.app.Fragment;

import com.projects.vkotov.todotestapp.presenter.BasePresenter;

/**
 * Created by skill on 28.02.2018.
 */

public abstract class BaseFragment extends Fragment {
    protected abstract BasePresenter getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }
}
