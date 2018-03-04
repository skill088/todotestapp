package com.projects.vkotov.todotestapp.view.fragments;

import com.projects.vkotov.todotestapp.Error;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;

/**
 * Created by skill on 01.03.2018.
 */

public interface TodoListView {

    void showLoading();

    void showError(String msg);

    void showTodo(TodoList todoList);

    void showLoadingMore();

    void hideLoadingMore();

    void prepareShowData();

    void setRefreshing(boolean refreshing);

    void showNoMore();

    void deleteItem(long id);

    void editeItem(long id, Todo todo);
}
