package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Environment;

public interface EnvironmentService extends ComponentOperations<Environment> {

    void addDatabase(Integer environmentId, Integer databaseId) throws ResourceNotExistsException,
            ParentAlreadyExistsException;
    void removeDatabase(Integer environmentId, Integer databaseId) throws ResourceNotExistsException;
    void addWorkspace(Integer environmentId, Integer workspaceId) throws ResourceNotExistsException;
    void removeWorkspace(Integer environmentId) throws ResourceNotExistsException;

}
