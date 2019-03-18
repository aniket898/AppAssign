package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.dao.SourceRepoRepository;
import com.aniket.homework.componentservice.dao.WorkspaceRepository;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.SourceRepository;
import com.aniket.homework.componentservice.model.Workspace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SourceRepositoryServiceImpl implements SourceRepositoryService {
    private static final Logger logger = LoggerFactory.getLogger(SourceRepositoryServiceImpl.class);

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private SourceRepoRepository sourceRepoRepository;

    @Override
    @Transactional
    public void addWorkspace(Integer sourceRepositoryId, Integer workspaceId) throws ResourceNotExistsException {
        SourceRepository sourceRepository = sourceRepoRepository.findById(sourceRepositoryId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find source repository with sourceRepositoryId=" + sourceRepositoryId));
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find source workspace with workspaceId=" + workspaceId));
        sourceRepository.linkParent(workspace);
    }

    @Override
    @Transactional
    public void removeWorkspace(Integer sourceRepositoryId) throws ResourceNotExistsException {
        SourceRepository sourceRepository = sourceRepoRepository.findById(sourceRepositoryId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find source repository with sourceRepositoryId=" + sourceRepositoryId));
        sourceRepository.unlinkParent();
    }

    @Override
    public SourceRepository get(Integer id) throws ResourceNotExistsException {
        return sourceRepoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotExistsException(
                        "Did not find source repository with sourceRepositoryId=" + id));
    }

    @Override
    public List<SourceRepository> getAll() {
        return sourceRepoRepository.findAll();
    }

    @Override
    public void remove(Integer id) throws ResourceNotExistsException {
        sourceRepoRepository.deleteById(id);
    }

    @Override
    public void create(SourceRepository sourceRepository) {
        sourceRepoRepository.save(sourceRepository);
    }

    @Override
    public void update(Integer id, SourceRepository sourceRepository) {
        SourceRepository repoSourceRepository = sourceRepoRepository.findById(id).orElse(sourceRepository);
        sourceRepoRepository.save(repoSourceRepository);
    }
}
