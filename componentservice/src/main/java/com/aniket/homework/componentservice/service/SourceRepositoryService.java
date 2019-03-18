package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.SourceRepository;

public interface SourceRepositoryService extends ComponentOperations<SourceRepository> {

    void addWorkspace(Integer sourceRepositoryId, Integer workspaceId) throws ResourceNotExistsException;
    void removeWorkspace(Integer sourceRepositoryId) throws ResourceNotExistsException ;

}