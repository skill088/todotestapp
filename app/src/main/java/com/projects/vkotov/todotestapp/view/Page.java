package com.projects.vkotov.todotestapp.view;

import com.projects.vkotov.todotestapp.presenter.IPresenter;
import com.projects.vkotov.todotestapp.view.fragments.ReloadedFragment;

/**
 * Created by skill on 02.03.2018.
 */

public interface Page<T> extends ReloadedFragment {
    void showError(Throwable e, IPresenter presenter);
    void snackError(Throwable e);
    void showData(T data);
    void showLoading();
    void showLoadingMore();
    void hideLoadingMore();
    void prepareShowData();
}
