package com.aniket.homework.componentservice.controller;

import com.aniket.homework.componentservice.exception.BadRequestException;
import com.aniket.homework.componentservice.exception.ParentAlreadyExistsException;
import com.aniket.homework.componentservice.exception.ResourceNotExistsException;
import com.aniket.homework.componentservice.model.ErrorResponse;
import com.aniket.homework.componentservice.model.Workspace;
import com.aniket.homework.componentservice.service.WorkspaceService;
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
public class WorkspaceController {
    private static final Logger logger = LoggerFactory.getLogger(WorkspaceController.class);

    @Autowired
    private WorkspaceService workspaceService;

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}",
            method= RequestMethod.GET)
    public @ResponseBody
    Workspace getWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId) {

        Workspace workspace;
        switch(Version.fromValue(version)){

            case V1:
                workspace = workspaceService.get(workspaceId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }

        if (workspace == null) {
            logger.error("Workspace not found for workspaceId=" + workspaceId);
            throw new ResourceNotExistsException("Workspace not found for workspaceId=" + workspaceId);
        }

        return workspace;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces",
            method= RequestMethod.GET)
    public @ResponseBody
    List<Workspace> getAllWorkspaces(
            @PathVariable(VERSION) String version) {

        List<Workspace> workspaces;
        switch(Version.fromValue(version)){

            case V1:
                workspaces = workspaceService.getAll();
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
        return workspaces;
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces",
            method= RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void createWorkspace(
            @PathVariable(VERSION) String version,
            @RequestBody Workspace workspace) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.create(workspace);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody void updateWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId,
            @RequestBody Workspace workspace) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.update(workspaceId, workspace);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void deleteWorkspace(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.remove(workspaceId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}/link/environments/{environmentId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void linkEnvironment(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId,
            @PathVariable(ENVIRONMENT_ID) int environmentId) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.addEnvironment(workspaceId, environmentId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}/unlink/environments/{environmentId}",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void unlinkEnvironment(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId,
            @PathVariable(ENVIRONMENT_ID) int environmentId) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.removeEnvironment(workspaceId, environmentId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}/link/sourcerepositories/{sourceRepositoryId}",
            method= RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody void linkSourceRepository(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId,
            @PathVariable(SOURCE_REPOSITORY_ID) int sourceRepositoryId) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.addSourceRepository(workspaceId, sourceRepositoryId);
                break;

            default:
                logger.error("Invalid version number");
                throw new ResourceNotExistsException("Invalid version number");
        }
    }

    @RequestMapping(value="/{version:[v|V][0-9]+}/workspaces/{workspaceId}/unlink/sourcerepositories/{sourceRepositoryId}",
            method= RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public @ResponseBody void unlinkSourceRepository(
            @PathVariable(VERSION) String version,
            @PathVariable(WORKSPACE_ID) int workspaceId,
            @PathVariable(SOURCE_REPOSITORY_ID) int sourceRepositoryId) {
        switch(Version.fromValue(version)){

            case V1:
                workspaceService.removeSourceRepository(workspaceId, sourceRepositoryId);
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
