package com.projects.vkotov.todotestapp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.other.di.view.DaggerViewComponent;
import com.projects.vkotov.todotestapp.other.di.view.ViewDynamicModule;
import com.projects.vkotov.todotestapp.presenter.BasePresenter;
import com.projects.vkotov.todotestapp.presenter.TodoListPresenter;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;
import com.projects.vkotov.todotestapp.view.adapters.TodoAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by skill on 01.03.2018.
 */

public class TodoListFragment extends BaseFragment implements TodoListView, SwipeRefreshLayout.OnRefreshListener{

    private final String TAG = "TodoListFragment";

    @BindView(R.id.loading)
    View loading;

//    @org.jetbrains.annotations.Nullable
    @BindView(R.id.loadingMore)
    View loadingMore;

    @BindView(R.id.error)
    TextView error;

    @BindView(R.id.retryButton)
    Button retryButton;

    @BindView(R.id.list)
    RecyclerView list;

    @BindView(R.id.constraint_layout)
    View layout;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    TodoListPresenter presenter;

    TodoAdapter adapter;

    public static TodoListFragment newInstance(long id) {
        TodoListFragment fragment = new TodoListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewDynamicModule(new ViewDynamicModule(this))
                    .build();
        }
        viewComponent.inject(this);
        presenter.onCreate(/*this, */savedInstanceState/*, getArguments()*/);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setStackFromEnd(true);
        list.setLayoutManager(layoutManager);
        adapter = new TodoAdapter(presenter);
        list.setAdapter(adapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        presenter.onCreateView();
        return view;
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showTodo(TodoList todoList) {
        if (todoList.getIsLastPage() == 1)
            Snackbar.make(layout, "No more items", Snackbar.LENGTH_LONG).show();
        prepareShowData();
        adapter.setItem(todoList);
        adapter.setList(todoList.getItems());
    }

    @Override
    public void showLoading() {
        list.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String msg) {
        list.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        error.setVisibility(View.VISIBLE);
        error.setText("Произошла ошибка при загрузке данных");
        error.setMovementMethod(LinkMovementMethod.getInstance());
        retryButton.setVisibility(View.VISIBLE/* : View.GONE*/);
        retryButton.setOnClickListener(view -> reload());
    }

    @Override
    public void showLoadingMore() {
        if (loadingMore != null) {
            loadingMore.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void prepareShowData() {
        loading.setVisibility(View.GONE);
        error.setVisibility(View.GONE);
        retryButton.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingMore() {
        if (loadingMore != null) {
            loadingMore.setVisibility(View.GONE);
            if (getView() != null) {
                View v = getView().findViewById(R.id.list);
                if (v instanceof RecyclerView) {
                    ((RecyclerView) v).smoothScrollBy(0, loadingMore.getHeight());
                }
            }
        }
    }

    public void reload() {
        Log.i(TAG, "reload()");
        presenter.reload();
    }

    @Override
    public void onRefresh() {
        reload();
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }
}
