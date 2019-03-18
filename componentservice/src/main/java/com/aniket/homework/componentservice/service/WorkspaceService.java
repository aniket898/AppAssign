package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Workspace;
import com.aniket.homework.componentservice.model.WorkspaceResponse;

import java.util.List;

public interface WorkspaceService extends ComponentOperations<Workspace> {

    void addEnvironment(Integer workspaceId, Integer environmentId) throws ResourceNotExistsException,
            ParentAlreadyExistsException;
    void removeEnvironment(Integer workspaceId, Integer environmentId) throws ResourceNotExistsException;
    void addSourceRepository(Integer workspaceId, Integer sourceRepositoryId) throws ResourceNotExistsException,
            ParentAlreadyExistsException;
    void removeSourceRepository(Integer workspaceId, Integer sourceRepositoryId) throws ResourceNotExistsException;
    WorkspaceResponse getWorkSpaceAndOwnerGroup(Integer workspaceId) throws ResourceNotExistsException;
    List<WorkspaceResponse> getAllWorkSpaceAndOwnerGroup() throws ResourceNotExistsException;
}