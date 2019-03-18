package com.aniket.homework.componentservice.controller;

import com.aniket.homework.componentservice.exception.BadRequestException;
import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Database;
import com.aniket.homework.componentservice.model.ErrorResponse;
import com.aniket.homework.componentservice.service.DatabaseService;
import com.aniket.homework.componentservice.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.aniket.homework.componentservice.util.Constants.DATABASE_ID;
import static com.aniket.homework.componentservice.util.Constants.ENVIRONMENT_ID;
import static com.aniket.homework.componentservice.util.Constants.VERSION;

@Controller
public class DatabaseController {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseController.class);

    @Autowired
    private DatabaseService databaseService;

    @RequestMapping(value="/{version:[v|V][0-9]+}/databases/{databaseId}",
            method= RequestMethod.GET)
    public @ResponseBody
    Database getDatabase(
            @PathVariable(VERSION) String version,
            @PathVariable(DATABASE_ID) int databaseId) {

        Database database;
        switch(Version.fromValue(version)){

            case V1:
                database = databaseService.get(databaseId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }

        if (database == null) {
            logger.error("Database not found for databaseId=" + databaseId);
            throw new ResourceNotExistsException("Database not found for databaseId=" + databaseId);
        }

        return database;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/databases",
            method= RequestMethod.GET)
    public @ResponseBody
    List<Database> getAllDatabases(
            @PathVariable(VERSION) String version) {

        List<Database> databases;
        switch(Version.fromValue(version)){

            case V1:
                databases = databaseService.getAll();
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
        return databases;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/databases",
            method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void createDatabase(
            @PathVariable(VERSION) String version,
            @RequestBody Database database) {
        switch(Version.fromValue(version)){

            case V1:
                databaseService.create(database);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/databases/{databaseId}",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void deleteDatabase(
            @PathVariable(VERSION) String version,
            @PathVariable(DATABASE_ID) int databaseId) {
        switch(Version.fromValue(version)){

            case V1:
                databaseService.remove(databaseId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/databases/{databaseId}/link/environments/{environmentId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void linkEnvironment(
            @PathVariable(VERSION) String version,
            @PathVariable(DATABASE_ID) int databaseId,
            @PathVariable(ENVIRONMENT_ID) int environmentId) {
        switch(Version.fromValue(version)){

            case V1:
                databaseService.addEnvironment(databaseId, environmentId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/databases/{databaseId}/unlink/environments",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void unlinkEnvironment(
            @PathVariable(VERSION) String version,
            @PathVariable(DATABASE_ID) int databaseId) {
        switch(Version.fromValue(version)){

            case V1:
                databaseService.removeEnvironment(databaseId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @ExceptionHandler(ResourceNotExistsException.class)
    public @ResponseBody
    ResponseEntity<ErrorResponse> handleResourceNotExistsException(HttpServletRequest req, Exception exception)
            throws ResourceNotExistsException {
        return new ResponseEntity<>(
                new ErrorResponse(exception.getMessage()),  HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadRequestException.class, ParentAlreadyExistsException.class})
    public @ResponseBody ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest req, Exception exception)
            throws Exception {
        return new ResponseEntity<>(
                new ErrorResponse(exception.getLocalizedMessage()),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, Exception exception)
            throws Exception {
        return new ResponseEntity<>(
                new ErrorResponse("Internal Server Error"),  HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
