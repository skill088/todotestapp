package com.projects.vkotov.todotestapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.presenter.TodoListPresenter;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by skill on 02.03.2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private final String TAG = "TodoAdapter";

    private TodoListPresenter presenter;
    protected TodoList todoList;
    protected List<Todo> list = new ArrayList<>();

    public TodoAdapter(TodoListPresenter presenter) {
        this.presenter = presenter;
        setHasStableIds(true);
    }

    public void setItem(TodoList obj) {
        todoList = obj;
        notifyDataSetChanged();
    }

    public void setList(List<Todo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final int layout = R.layout.todo_list_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo todo = list.get(position);

        holder.content.setText(todo.getContent());
        switch (todo.getIsCompleted()) {
            case 0: holder.isCompleted.setChecked(false); break;
            case 1: holder.isCompleted.setChecked(true); break;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)       public View container;
        @BindView(R.id.content)         TextView content;
        @BindView(R.id.is_completed)    CheckBox isCompleted;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position > list.size() - Constants.LOAD_THRESHOLD) {
            presenter.getTodoPage(position == list.size() - 1);
        }
        return list.get(position).getId();
    }
}
