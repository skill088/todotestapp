package com.projects.vkotov.todotestapp.other.di;

import com.projects.vkotov.todotestapp.model.ModelImpl;
import com.projects.vkotov.todotestapp.other.App;
import com.projects.vkotov.todotestapp.presenter.BasePresenter;
import com.projects.vkotov.todotestapp.presenter.LoginPresenter;
import com.projects.vkotov.todotestapp.presenter.TodoListPresenter;
import com.projects.vkotov.todotestapp.view.fragments.LoginFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by skill on 01.03.2018.
 */
@Singleton
@Component(modules = {ModelModule.class, PresenterModule.class, ViewModule.class})
public interface AppComponent {

    void inject(App app);

    void inject(ModelImpl dataRepository);

    void inject(BasePresenter basePresenter);

    void inject(LoginPresenter loginPresenter);

    void inject(TodoListPresenter todoListPresenter);

}
