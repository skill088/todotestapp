package com.projects.vkotov.todotestapp.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.projects.vkotov.todotestapp.Constants;
import com.projects.vkotov.todotestapp.R;
import com.projects.vkotov.todotestapp.presenter.TodoListPresenter;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by skill on 02.03.2018.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private final String TAG = "TodoAdapter";

    private TodoListPresenter presenter;
    protected TodoList todoList;
    protected List<Todo> list = new ArrayList<>();
    protected List<Todo> listCopy = new ArrayList<>();
    protected List<Todo> templist = new ArrayList<>();

    public TodoAdapter(TodoListPresenter presenter) {
        this.presenter = presenter;
        setHasStableIds(true);
    }

    public void setItem(TodoList obj) {
        todoList = obj;
        notifyDataSetChanged();
    }

    public void setList(List<Todo> list) {
        listCopy.clear();
        this.list = list;
        listCopy.addAll(list);
        templist.addAll(list);
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
        holder.isCompleted.setTag(todo.getId());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void deleteItem(long id) {
        Todo candidat = null;
        for (Todo item : list)
            if (item.getId() == id)
                candidat = item;
        if (candidat != null)
            list.remove(candidat);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.container)       public View container;
        @BindView(R.id.bottom_views)    View bottomViews;
        @BindView(R.id.content)         TextView content;
        @BindView(R.id.is_completed)    CheckBox isCompleted;
        @BindView(R.id.swipe)           SwipeLayout swipeLayout;
        @BindView(R.id.delete)          ImageView delete;
        @BindView(R.id.edit)            ImageView edit;

        @OnClick(R.id.is_completed)
        public void complete(View view) {
            presenter.complete((long) view.getTag());
        }

        @OnClick(R.id.delete)
        public void deleteItem() {
            presenter.deleteItem((long) isCompleted.getTag());
        }

        @OnClick(R.id.edit)
        public void editItem() {
            presenter.editItem((long) isCompleted.getTag());
        }

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            swipeLayout.addDrag(SwipeLayout.DragEdge.Left, bottomViews);
        }
    }

    @Override
    public long getItemId(int position) {
        if (position > list.size() - Constants.LOAD_THRESHOLD) {
            presenter.getTodoPage(position == list.size() - 1);
        }
        return list.get(position).getId();
    }

    public void filter(String text) {
        list.clear();
        if(text.isEmpty()){
            list.addAll(templist);
        } else {
            text = text.toLowerCase();
            for(Todo item: templist){
                if(item.getContent().toLowerCase().contains(text)){
                    list.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    public void filterChecked() {
        templist.clear();
        list.clear();
        for (Todo item : listCopy) {
            if (item.getIsCompleted() == 1)
                list.add(item);
        }
        templist.addAll(list);
        notifyDataSetChanged();
    }

    public void filterUnchecked() {
        templist.clear();
        list.clear();
        for (Todo item : listCopy) {
            if (item.getIsCompleted() == 0)
                list.add(item);
        }
        templist.addAll(list);
        notifyDataSetChanged();
    }

    public void resetFilters() {
        list.clear();
        list.addAll(listCopy);
        notifyDataSetChanged();
    }
}
