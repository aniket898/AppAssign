package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.model.Component;

public interface IParentLinkable<T extends Component> {

    public boolean linkParent(T component) throws UnsupportedOperationException;
//    public boolean unlinkParent(T component) throws UnsupportedOperationException;
    public boolean unlinkParent() throws UnsupportedOperationException;
}
