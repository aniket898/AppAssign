package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.dao.DatabaseRepository;
import com.aniket.homework.componentservice.dao.EnvironmentRepository;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Database;
import com.aniket.homework.componentservice.model.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatabaseServiceImpl implements DatabaseService {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseServiceImpl.class);

    @Autowired
    private DatabaseRepository databaseRepository;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Override
    @Transactional
    public void addEnvironment(Integer databaseId, Integer environmentId) throws ResourceNotExistsException {
        Database database = databaseRepository.findById(databaseId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find database with databaseId=" + databaseId));
        Environment environment = environmentRepository.findById(environmentId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find environment with environmentId=" + environmentId));
        database.linkParent(environment);
    }

    @Override
    @Transactional
    public void removeEnvironment(Integer databaseId) throws ResourceNotExistsException {
        Database database = databaseRepository.findById(databaseId).orElseThrow(() ->
                new ResourceNotExistsException("Did not find database with databaseId=" + databaseId));
        database.unlinkParent();
    }

    @Override
    public Database get(Integer id) throws ResourceNotExistsException{
        return databaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotExistsException(
                        "Did not find database with databaseId=" + id));
    }

    @Override
    public List<Database> getAll() {
        return databaseRepository.findAll();
    }

    @Override
    public void remove(Integer id) throws ResourceNotExistsException {
        databaseRepository.deleteById(id);
    }

    @Override
    public void create(Database database) {
        databaseRepository.save(database);
    }

    @Override
    public void update(Integer id, Database database) {
        Database repoDatabase = databaseRepository.findById(id).orElse(database);
        databaseRepository.save(repoDatabase);
    }
}
