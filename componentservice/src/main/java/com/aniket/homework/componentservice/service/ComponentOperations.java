package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Component;

import java.util.List;

public interface ComponentOperations<T extends Component> {
    T get(Integer id) throws ResourceNotExistsException;
    List<T> getAll();
    void remove(Integer id) throws ResourceNotExistsException;
    void create(T t);
    void update(Integer id, T t);
}
