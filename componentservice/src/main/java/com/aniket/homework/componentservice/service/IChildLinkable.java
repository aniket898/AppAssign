package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.model.Component;

public interface IChildLinkable<T extends Component> {

    public boolean linkChild(T component) throws UnsupportedOperationException;
    public boolean unlinkChild(T component) throws UnsupportedOperationException;
}
