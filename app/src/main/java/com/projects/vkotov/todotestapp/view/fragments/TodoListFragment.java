package com.projects.vkotov.todotestapp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.other.di.view.DaggerViewComponent;
import com.projects.vkotov.todotestapp.other.di.view.ViewDynamicModule;
import com.projects.vkotov.todotestapp.presenter.BasePresenter;
import com.projects.vkotov.todotestapp.presenter.TodoListPresenter;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;
import com.projects.vkotov.todotestapp.view.adapters.TodoAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by skill on 01.03.2018.
 */

public class TodoListFragment extends BaseFragment implements TodoListView, SwipeRefreshLayout.OnRefreshListener{

    private final String TAG = "TodoListFragment";

    @BindView(R.id.loading)         View loading;
    @BindView(R.id.loadingMore)     View loadingMore;
    @BindView(R.id.error)           TextView error;
    @BindView(R.id.retryButton)     Button retryButton;
    @BindView(R.id.list)            RecyclerView list;
    @BindView(R.id.relative_layout) View layout;
    @BindView(R.id.swipe_container) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.checkedViews)    View checkedViews;
    @BindView(R.id.checked)         CheckedTextView checked;
    @BindView(R.id.unchecked)       CheckedTextView unchecked;

    @OnClick(R.id.checked)
    public void filterChecked(CheckedTextView view) {
        if (!view.isChecked()){
            if (unchecked.isChecked())
                unchecked.setChecked(false);
            view.setChecked(true);
            adapter.filterChecked();
        } else  {
            view.setChecked(false);
            adapter.resetFilters();
        }
    }

    @OnClick(R.id.unchecked)
    public void filterUhecked(CheckedTextView view) {
        if (!view.isChecked()){
            if (checked.isChecked())
                checked.setChecked(false);
            view.setChecked(true);
            adapter.filterUnchecked();
        } else {
            view.setChecked(false);
            adapter.resetFilters();
        }
    }

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
        setHasOptionsMenu(true);
        presenter.onCreateView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuItem menuItem = menu.findItem(R.id.search);
        if (menuItem != null) {
            menuItem.setVisible(true);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setOnSearchClickListener(view -> checkedViews.setVisibility(View.VISIBLE));
//            searchView.setOnClickListener(view -> checkedViews.setVisibility(View.VISIBLE));
            searchView.setOnCloseListener(() -> {
                checkedViews.setVisibility(View.GONE);
                return false;
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
//                    adapter.filter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.filter(newText);
                    return true;
                }
            });
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    protected BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public void showTodo(TodoList todoList) {
        prepareShowData();
        adapter.setItem(todoList);
        adapter.setList(todoList.getItems());
    }

    @Override
    public void showNoMore() {
        Snackbar.make(layout, "No more items", Snackbar.LENGTH_LONG).show();
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

    @Override
    public void deleteItem(long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete");
        builder.setMessage("Are you sure?");
        builder.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton("Yes", (dialogInterface, i) -> adapter.deleteItem(id));
        builder.create().show();
    }

    @Override
    public void editeItem(long id, Todo todo) {
        Snackbar.make(layout, "Editing items is under development", Snackbar.LENGTH_LONG).show();
    }
}
