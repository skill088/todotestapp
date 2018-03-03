package com.projects.vkotov.todotestapp.other.di.view;

import com.projects.vkotov.todotestapp.presenter.LoginPresenter;
import com.projects.vkotov.todotestapp.presenter.TodoListPresenter;
import com.projects.vkotov.todotestapp.view.ActivityCallback;
import com.projects.vkotov.todotestapp.view.fragments.LoginView;
import com.projects.vkotov.todotestapp.view.fragments.TodoListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by skill on 01.03.2018.
 */

@Module
public class ViewDynamicModule {

    private LoginView loginView;

    private TodoListView todoListView;

    private ActivityCallback activityCallback;

    public ViewDynamicModule(LoginView loginView, ActivityCallback activityCallback) {
        this.loginView = loginView;
        this.activityCallback = activityCallback;
    }

    public ViewDynamicModule(TodoListView todoListView) {
        this.todoListView = todoListView;
    }

    @Provides
    LoginPresenter provideLoginPresenter() {
        return new LoginPresenter(loginView, activityCallback);
    }

    @Provides
    TodoListPresenter provideTodoListPresenter() {
        return new TodoListPresenter(todoListView);
    }
}
