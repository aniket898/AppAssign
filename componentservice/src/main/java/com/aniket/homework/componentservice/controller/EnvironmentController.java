package com.aniket.homework.componentservice.controller;

import com.aniket.homework.componentservice.exception.BadRequestException;
import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.Environment;
import com.aniket.homework.componentservice.model.ErrorResponse;
import com.aniket.homework.componentservice.service.EnvironmentService;
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

import static com.aniket.homework.componentservice.util.Constants.*;

@Controller
public class EnvironmentController {
    private static final Logger logger = LoggerFactory.getLogger(EnvironmentController.class);

    @Autowired
    private EnvironmentService environmentService;

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments/{environmentId}",
            method= RequestMethod.GET)
    public @ResponseBody
    Environment getEnvironment(
            @PathVariable(VERSION) String version,
            @PathVariable(ENVIRONMENT_ID) int environmentId) {

        Environment environment;
        switch(Version.fromValue(version)){

            case V1:
                environment = environmentService.get(environmentId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }

        if (environment == null) {
            logger.error("Environment not found for environmentId=" + environmentId);
            throw new ResourceNotExistsException("Environment not found for environmentId=" + environmentId);
        }

        return environment;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments",
            method= RequestMethod.GET)
    public @ResponseBody
    List<Environment> getAllEnvironments(
            @PathVariable(VERSION) String version) {
        List<Environment> environments;
        switch(Version.fromValue(version)) {

            case V1:
                environments = environmentService.getAll();
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
        return environments;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments",
            method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void createEnvironment(
            @PathVariable(VERSION) String version,
            @RequestBody Environment environment) {
        switch(Version.fromValue(version)){

            case V1:
                environmentService.create(environment);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments/{environmentId}",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void deleteEnvironment(
            @PathVariable(VERSION) String version,
            @PathVariable(ENVIRONMENT_ID) int environmentId) {
        switch(Version.fromValue(version)){

            case V1:
                environmentService.remove(environmentId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments/{environmentId}/link/workspaces/{workspaceId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void linkWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(ENVIRONMENT_ID) int environmentId,
            @PathVariable(WORKSPACE_ID) int workspaceId) {
        switch(Version.fromValue(version)){

            case V1:
                environmentService.addWorkspace(environmentId, workspaceId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments/{environmentId}/unlink/workspaces",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void unlinkWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(ENVIRONMENT_ID) int environmentId) {
        switch(Version.fromValue(version)){

            case V1:
                environmentService.removeWorkspace(environmentId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }


    @RequestMapping(value="/{version:[v|V][0-9]+}/environments/{environmentId}/link/databases/{databaseId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void linkDatabase(
            @PathVariable(VERSION) String version,
            @PathVariable(ENVIRONMENT_ID) int environmentId,
            @PathVariable(DATABASE_ID) int databaseId) {
        switch(Version.fromValue(version)){

            case V1:
                environmentService.addDatabase(environmentId, databaseId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/environments/{environmentId}/unlink/databases/{databaseId}",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void unlinkDatabase(
            @PathVariable(VERSION) String version,
            @PathVariable(ENVIRONMENT_ID) int environmentId,
            @PathVariable(DATABASE_ID) int databaseId) {
        switch(Version.fromValue(version)){

            case V1:
                environmentService.removeDatabase(environmentId, databaseId);
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
