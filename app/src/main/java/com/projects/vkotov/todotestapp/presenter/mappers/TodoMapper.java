package com.projects.vkotov.todotestapp.presenter.mappers;

import com.projects.vkotov.todotestapp.model.dto.TodoDTO;
import com.projects.vkotov.todotestapp.model.dto.TodoListDTO;
import com.projects.vkotov.todotestapp.presenter.vo.Todo;
import com.projects.vkotov.todotestapp.presenter.vo.TodoList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by skill on 02.03.2018.
 */

public class TodoMapper implements Function<TodoListDTO, TodoList> {

    @Inject
    public TodoMapper() {}

    @Override
    public TodoList apply(TodoListDTO todoListDTO) throws Exception {
        List<Todo> list;
        if (todoListDTO.getItems() != null) {
            list = Observable.fromIterable(todoListDTO.getItems())
                    .map(listMapper)
                    .toList().blockingGet();
        } else {
            list = new ArrayList<>();
        }
        TodoList todoList = new TodoList();
        todoList.setTotal(todoListDTO.getTotal());
        todoList.setIsLastPage(todoListDTO.getIsLastPage());
        todoList.setItems(list);
        return todoList;
    }

    private Function<TodoDTO, Todo> listMapper = todoDTOS -> {
        Todo todo = new Todo();
        todo.setId(todoDTOS.getId());
        todo.setContent(todoDTOS.getContent());
        todo.setIsCompleted(todoDTOS.getIsCompleted());
        String[] temp = todoDTOS.getCreatedAt().split("-");
        todo.setCreatedAt(Long.parseLong(temp[0])*31536000000L +
                Long.parseLong(temp[1])*2592000000L + Long.parseLong(temp[2])*86400000L);
        temp = todoDTOS.getUpdatedAt().split("-");
        todo.setUpdatedAt(Long.parseLong(temp[0])*31536000000L +
                Long.parseLong(temp[1])*2592000000L + Long.parseLong(temp[2])*86400000L);
        todo.setUserId(todoDTOS.getUserId());
        return todo;
    };
}
