package com.projects.vkotov.todotestapp.presenter;

import android.os.Bundle;
import android.util.Log;

import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.Error;
import com.projects.vkotov.todotestapp.Prefs;
import com.projects.vkotov.todotestapp.other.App;
import com.projects.vkotov.todotestapp.presenter.mappers.TodoMapper;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;
import com.projects.vkotov.todotestapp.view.fragments.TodoListView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by skill on 01.03.2018.
 */

public class TodoListPresenter extends BasePresenter {

    private final String TAG = "TodoListPresenter";
    private static final String ITEM_KEY = "ITEM_KEY";
    private static final String CAN_LOAD_MORE_KEY = "CAN_LOAD_MORE_KEY";

    @Inject
    TodoMapper mapper;

    private TodoListView view;
    private boolean isLoading = false;
    protected boolean canLoadMore = true;
    private String token;

    private TodoList todoObj;
    List<Todo> items;

    public TodoListPresenter() {
    }

    public boolean isLoading() {
        return isLoading;
    }

    public TodoListPresenter(TodoListView view) {
        this.view = view;
        App.getComponent().inject(this);
        token = Prefs.getToken(context);
    }

    protected boolean todoListIsNull() {
        return (todoObj == null);
    }

    protected boolean todoListEmpty() {
        return (todoObj == null || todoObj.getItems().isEmpty());
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            todoObj = (TodoList) savedInstanceState.getSerializable(ITEM_KEY);
            canLoadMore = savedInstanceState.getBoolean(CAN_LOAD_MORE_KEY, true);
        }
    }

    public void onCreateView() {
        if (todoListIsNull()) {
            getTodo(1);
        } else {
            view.showTodo(todoObj);
        }
    }

    public void getTodo(int page) {
        if (mapper == null) {
            throw new RuntimeException("Mapper is null");
        }
        Log.i(TAG, "Loading first page");
        isLoading = true;
        view.showLoading();  // TODO: 02.03.2018 припилить
        model.getTodoList(page, token)
                .map(mapper)
                .subscribe(new Observer<TodoList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(TodoList todoListDTO) {
                        if (todoListDTO != null) {
                            if (todoListDTO.getIsLastPage() == 1)
                                canLoadMore = false;
                            Log.i(TAG, "onNext(): " + String.valueOf(todoListDTO.toString()));
                            todoObj = todoListDTO;
                            view.showTodo(todoObj); // TODO: 02.03.2018 припилить
                        } else {
                            Log.i(TAG, "onNext(): null or empty");
                            view.showError(new Error.NoItems("No more items").getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof Error.NoItems) {
                            canLoadMore = false;
                            view.showError(e.getMessage());
                        }
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        isLoading = false;
                        view.setRefreshing(false);
                    }
        });
    }

    public void getTodoPage(boolean showLoadingMore) {
//        Log.i(TAG, "in getTodoPage()");
        if (!canLoadMore || todoListEmpty()) {
            return;
        }
        if (showLoadingMore) {
            view.showLoadingMore();
        }
        if (isLoading()) {
            return;
        }
        int pageNum = ((todoObj.getItems().size() - 1) / Constants.DEFAULT_COUNT) + 2;
        isLoading = true;
        model.getTodoList(pageNum, token)
                .map(mapper)
                .subscribe(new Observer<TodoList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(TodoList todoList) {
                        if (todoList.getIsLastPage() == 1) {
                            canLoadMore = false;
                            todoObj.setIsLastPage(1);
                            view.showNoMore();
                        }
                        if (todoList.getItems() != null && !todoList.getItems().isEmpty()) {
                            todoObj.getItems().addAll(todoList.getItems());
                            view.showTodo(todoObj);
                        } else {
                            canLoadMore = false;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        canLoadMore = false;
                        if (!(e instanceof Error.NoItems)) {
                            view.showError(e.getMessage());
                        }
                        view.hideLoadingMore();
                        isLoading = false;
                    }

                    @Override
                    public void onComplete() {
                        view.hideLoadingMore();
                        isLoading = false;
                    }
                });
    }

    public void reload() {
        this.todoObj = null;
        getTodo(1);
    }

    public void complete(long id) {
        for (Todo item : todoObj.getItems()) {
            if (item.getId() == id) {
                int status = (item.getIsCompleted()==1)?0:1;
                item.setIsCompleted(status);
            }
        }
        view.showTodo(todoObj);
    }

    public void deleteItem(long id) {
        view.deleteItem(id);
    }

    public void editItem(long id) {
        Todo todo = null;
        for (Todo todo1 : todoObj.getItems()) {
            if (todo1.getId() == id)
                todo = todo1;
        }
        view.editItem(id, todo);
    }
}
