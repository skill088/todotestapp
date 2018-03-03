package com.projects.vkotov.todotestapp.other.di.view;

import com.projects.vkotov.todotestapp.view.fragments.LoginFragment;
import com.projects.vkotov.todotestapp.view.fragments.TodoListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by skill on 01.03.2018.
 */

@Singleton
@Component(modules = {ViewDynamicModule.class})
public interface ViewComponent {

    void inject(LoginFragment loginFragment);
    void inject(TodoListFragment todoListFragment);
}
