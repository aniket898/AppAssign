package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.dao.EnvironmentRepository;
import com.aniket.homework.componentservice.dao.SourceRepoRepository;
import com.aniket.homework.componentservice.dao.WorkspaceRepository;
import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceServiceImpl.class);

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private SourceRepoRepository sourceRepoRepository;

    @Autowired
    private DirectoryService directoryService;

    @Override
    @Transactional
    public void addEnvironment(Integer workspaceId, Integer environmentId) throws ResourceNotExistsException,
            ParentAlreadyExistsException {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find workspace with workspaceId=" + workspaceId));
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find environment with environmentId=" + environmentId));
        if(environment.getWorkspace() != null) {
            if(environment.getWorkspace().getWorkspaceId() == workspaceId)
                return;
            throw new ParentAlreadyExistsException("environmentId=" + environmentId + " is already attached to " +
                    "workspaceId=" + environment.getWorkspace().getWorkspaceId());
        } else {
            workspace.linkChild(environment);
        }
    }

    @Override
    @Transactional
    public void removeEnvironment(Integer workspaceId, Integer environmentId) throws ResourceNotExistsException {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find workspace with workspaceId=" + workspaceId));
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find environment with environmentId=" + environmentId));
        if(workspace.getEnvironments().contains(environment)){
            workspace.unlinkChild(environment);
        }
    }

    @Override
    @Transactional
    public void addSourceRepository(Integer workspaceId, Integer sourceRepositoryId) throws ResourceNotExistsException,
            ParentAlreadyExistsException {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find workspace with workspaceId=" + workspaceId));
        SourceRepository sourceRepository = sourceRepoRepository.findById(sourceRepositoryId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find sourcerepository with sourceRepositoryId=" + sourceRepositoryId));
        if(sourceRepository.getWorkspace() != null) {
            if(sourceRepository.getWorkspace().getWorkspaceId() == workspaceId)
                return;
            throw new ParentAlreadyExistsException("sourceRepositoryId=" + sourceRepositoryId + " is already attached to " +
                    "workspaceId=" + sourceRepository.getWorkspace().getWorkspaceId());
        } else {
            workspace.linkChild(sourceRepository);
        }

    }

    @Override
    @Transactional
    public void removeSourceRepository(Integer workspaceId, Integer sourceRepositoryId)  throws ResourceNotExistsException {
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find workspace with workspaceId=" + workspaceId));
        SourceRepository sourceRepository = sourceRepoRepository.findById(sourceRepositoryId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find sourcerepository with sourceRepositoryId=" + sourceRepositoryId));
        if(workspace.getSourceRepositories().contains(sourceRepository)) {
            workspace.unlinkChild(sourceRepository);
        }
    }

    @Override
    public WorkspaceResponse getWorkSpaceAndOwnerGroup(Integer workspaceId) throws ResourceNotExistsException {
        Workspace workspace = get(workspaceId);
        OwnerGroup ownerGroup = getOwnerGroup(workspace.getOwnerGroupId());
        return new WorkspaceResponse(workspace.getWorkspaceId(), workspace.getWorkspaceName(),
                workspace.getEnvironments(), workspace.getSourceRepositories(), ownerGroup);
    }

    public OwnerGroup getOwnerGroup(String ownerGroupId){
        OwnerGroup ownerGroup = null;
        try {
            CompletableFuture<OwnerGroup> ownerGroupCompletableFuture = directoryService.getOwnerGroupsById(ownerGroupId);
            ownerGroup = ownerGroupCompletableFuture.get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ownerGroup;
    }

    @Override
    public List<WorkspaceResponse> getAllWorkSpaceAndOwnerGroup() throws ResourceNotExistsException {
        List<Workspace> workspaces = getAll();
        List<WorkspaceResponse> workspaceResponses = new ArrayList<>();
        for (Workspace workspace: workspaces){
            OwnerGroup ownerGroup = getOwnerGroup(workspace.getOwnerGroupId());
            WorkspaceResponse workspaceResponse = new WorkspaceResponse(workspace.getWorkspaceId(), workspace.getWorkspaceName(),
                    workspace.getEnvironments(), workspace.getSourceRepositories(), ownerGroup);
            workspaceResponses.add(workspaceResponse);
        }
        return workspaceResponses;
    }

    @Override
    public Workspace get(Integer id) throws ResourceNotExistsException {
        return workspaceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotExistsException(
                        "Did not find workspace with workspaceId=" + id));

    }

    @Override
    public List<Workspace> getAll() {
        return workspaceRepository.findAll();
    }

    @Override
    public void remove(Integer id) throws ResourceNotExistsException {
        workspaceRepository.deleteById(id);
    }

    @Override
    public void create(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    @Override
    @Transactional
    public void update(Integer id, Workspace workspace) {
        if(workspaceRepository.findById(id).isPresent()) {
            Workspace repoWorkspace = workspaceRepository.findById(id).get();
            repoWorkspace.setWorkspaceName(workspace.getWorkspaceName());
            repoWorkspace.setOwnerGroupId(workspace.getOwnerGroupId());
            workspaceRepository.save(repoWorkspace);
        } else {
            workspaceRepository.save(workspace);
        }

    }
}
