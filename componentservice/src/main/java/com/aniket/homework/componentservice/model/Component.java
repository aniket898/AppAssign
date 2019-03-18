package com.aniket.homework.componentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Component {

    private String type;
    public String isOfType(){
        return null;
    }
}
