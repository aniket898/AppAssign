package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.dao.DatabaseRepository;
import com.aniket.homework.componentservice.dao.EnvironmentRepository;
import com.aniket.homework.componentservice.dao.WorkspaceRepository;
import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Database;
import com.aniket.homework.componentservice.model.Environment;
import com.aniket.homework.componentservice.model.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnvironmentServiceImpl implements EnvironmentService {


    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private DatabaseRepository databaseRepository;

    @Override
    @Transactional
    public void addDatabase(Integer environmentId, Integer databaseId) throws ResourceNotExistsException,
            ParentAlreadyExistsException {
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find environment with environmentId=" + environmentId));
        Database database = databaseRepository.findById(databaseId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find database with databaseId=" + databaseId));
        if(database.getEnvironment() != null) {
            if(database.getEnvironment().getEnvironmentId() == environmentId)
                return;
            throw new ParentAlreadyExistsException("databaseId=" + databaseId + " is already attached to " +
                    "environmentId=" + database.getEnvironment().getEnvironmentId());
        } else {
            environment.linkChild(database);
        }
    }

    @Override
    @Transactional
    public void removeDatabase(Integer environmentId, Integer databaseId) throws ResourceNotExistsException {
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find environment with environmentId=" + environmentId));
        Database database = databaseRepository.findById(databaseId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find database with databaseId=" + databaseId));
        if(environment.getDatabases().contains(database)) {
            environment.unlinkChild(database);
        }
    }

    @Override
    @Transactional
    public void addWorkspace(Integer environmentId, Integer workspaceId) throws ResourceNotExistsException {
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find environment with environmentId=" + environmentId));
        Workspace workspace = workspaceRepository.findById(workspaceId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find workspace with workspaceId=" + workspaceId));
        environment.linkParent(workspace);
    }

    @Override
    @Transactional
    public void removeWorkspace(Integer environmentId) throws ResourceNotExistsException {
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() -> new ResourceNotExistsException(
                "Did not find environment with environmentId=" + environmentId));
        environment.unlinkParent();
    }

    @Override
    public Environment get(Integer id) {
        return environmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotExistsException(
                        "Did not find environment with environmentId=" + id));
    }

    @Override
    public List<Environment> getAll() {
        return environmentRepository.findAll();
    }

    @Override
    public void remove(Integer id) {
        environmentRepository.deleteById(id);
    }

    @Override
    public void create(Environment environment) {
        environmentRepository.save(environment);
    }

    @Override
    public void update(Integer id, Environment environment) {
        Environment repoEnvironment = environmentRepository.findById(id).orElse(environment);
        environmentRepository.save(repoEnvironment);
    }
}
