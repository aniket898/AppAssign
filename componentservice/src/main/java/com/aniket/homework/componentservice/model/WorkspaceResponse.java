package com.aniket.homework.componentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkspaceResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private int workspaceId ;
    private String workspaceName ;
    private Set<Environment> environments;
    private Set<SourceRepository> sourceRepositories;
    private OwnerGroup ownerGroup;

    public WorkspaceResponse(int workspaceId, String workspaceName, Set<Environment> environments, Set<SourceRepository> sourceRepositories, OwnerGroup ownerGroup) {
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.environments = environments;
        this.sourceRepositories = sourceRepositories;
        this.ownerGroup = ownerGroup;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public Set<Environment> getEnvironments() {
        return environments;
    }

    public void setEnvironments(Set<Environment> environments) {
        this.environments = environments;
    }

    public Set<SourceRepository> getSourceRepositories() {
        return sourceRepositories;
    }

    public void setSourceRepositories(Set<SourceRepository> sourceRepositories) {
        this.sourceRepositories = sourceRepositories;
    }

    public OwnerGroup getOwnerGroup() {
        return ownerGroup;
    }

    public void setOwnerGroup(OwnerGroup ownerGroup) {
        this.ownerGroup = ownerGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkspaceResponse that = (WorkspaceResponse) o;

        if (workspaceId != that.workspaceId) return false;
        if (workspaceName != null ? !workspaceName.equals(that.workspaceName) : that.workspaceName != null)
            return false;
        if (environments != null ? !environments.equals(that.environments) : that.environments != null) return false;
        if (sourceRepositories != null ? !sourceRepositories.equals(that.sourceRepositories) : that.sourceRepositories != null)
            return false;
        return ownerGroup != null ? ownerGroup.equals(that.ownerGroup) : that.ownerGroup == null;
    }

    @Override
    public int hashCode() {
        int result = workspaceId;
        result = 31 * result + (workspaceName != null ? workspaceName.hashCode() : 0);
        result = 31 * result + (environments != null ? environments.hashCode() : 0);
        result = 31 * result + (sourceRepositories != null ? sourceRepositories.hashCode() : 0);
        result = 31 * result + (ownerGroup != null ? ownerGroup.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WorkspaceResponse{" +
                "workspaceId=" + workspaceId +
                ", workspaceName='" + workspaceName + '\'' +
                ", environments=" + environments +
                ", sourceRepositories=" + sourceRepositories +
                ", ownerGroup=" + ownerGroup +
                '}';
    }
}
