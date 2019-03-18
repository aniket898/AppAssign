package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Database;

public interface DatabaseService extends ComponentOperations<Database> {

    void addEnvironment(Integer databaseId, Integer environmentId) throws ResourceNotExistsException;
    void removeEnvironment(Integer databaseId) throws ResourceNotExistsException;

}
