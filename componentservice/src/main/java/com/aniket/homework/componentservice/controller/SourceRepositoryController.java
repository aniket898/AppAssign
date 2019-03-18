package com.aniket.homework.componentservice.controller;

import com.aniket.homework.componentservice.exception.BadRequestException;
import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.ErrorResponse;
import com.aniket.homework.componentservice.model.SourceRepository;
import com.aniket.homework.componentservice.service.SourceRepositoryService;
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

import static com.aniket.homework.componentservice.util.Constants.SOURCE_REPOSITORY_ID;
import static com.aniket.homework.componentservice.util.Constants.VERSION;
import static com.aniket.homework.componentservice.util.Constants.WORKSPACE_ID;

@Controller
public class SourceRepositoryController {
    private static final Logger logger = LoggerFactory.getLogger(SourceRepositoryController.class);

    @Autowired
    private SourceRepositoryService sourceRepositoryService;

    @RequestMapping(value="/{version:[v|V][0-9]+}/sourcerepositories/{sourceRepositoryId}",
            method= RequestMethod.GET)
    public @ResponseBody
    SourceRepository getSourceRepository(
            @PathVariable(VERSION) String version,
            @PathVariable(SOURCE_REPOSITORY_ID) int sourceRepositoryId) {

        SourceRepository sourceRepository;
        switch(Version.fromValue(version)){

            case V1:
                sourceRepository = sourceRepositoryService.get(sourceRepositoryId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }

        if (sourceRepository == null) {
            logger.error("SourceRepository not found for sourceRepositoryId=" + sourceRepositoryId);
            throw new ResourceNotExistsException("SourceRepository not found for sourceRepositoryId=" + sourceRepositoryId);
        }

        return sourceRepository;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/sourcerepositories",
            method= RequestMethod.GET)
    public @ResponseBody
    List<SourceRepository> getAllSourceRepositories(
            @PathVariable(VERSION) String version) {

        List<SourceRepository>  sourceRepositories;
        switch(Version.fromValue(version)){

            case V1:
                sourceRepositories = sourceRepositoryService.getAll();
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
        return sourceRepositories;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/sourcerepositories/{sourceRepositoryId}/unlink/workspaces",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void unlinkWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(SOURCE_REPOSITORY_ID) int sourceRepositoryId) {
        switch(Version.fromValue(version)){

            case V1:
                sourceRepositoryService.removeWorkspace(sourceRepositoryId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }


    @RequestMapping(value="/{version:[v|V][0-9]+}/sourcerepositories/{sourceRepositoryId}/link/workspaces/{workspaceId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void linkWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(SOURCE_REPOSITORY_ID) int sourceRepositoryId,
            @PathVariable(WORKSPACE_ID) int workspaceId) {
        switch(Version.fromValue(version)){

            case V1:
                sourceRepositoryService.addWorkspace(sourceRepositoryId, workspaceId);
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
