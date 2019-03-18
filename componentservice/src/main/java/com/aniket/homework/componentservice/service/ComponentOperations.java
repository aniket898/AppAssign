package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.model.Component;

import java.util.List;

public interface ComponentOperations<T extends Component> {
    T get(Integer id);
    List<T> getAll();
    void remove(Integer id);
    void create(T t);
    void update(Integer id, T t);
}
