package com.aniket.homework.componentservice.model;

import com.aniket.homework.componentservice.service.IChildLinkable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Workspace", catalog = "component_schema")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Workspace extends Component implements Serializable, IChildLinkable<Component> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="WorkspaceId")
    private int workspaceId ;

    @Column(name = "WorkspaceName")
    private String workspaceName ;

    @Column(name = "OwnerGroupId")
    private String ownerGroupId;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workspace", cascade={CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference
    private Set<Environment> environments;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "workspace", cascade={CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference
    private Set<SourceRepository> sourceRepositories;

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

    public String getOwnerGroupId() {
        return ownerGroupId;
    }

    public void setOwnerGroupId(String ownerGroupId) {
        this.ownerGroupId = ownerGroupId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Workspace workspace = (Workspace) o;

        if (workspaceId != workspace.workspaceId) return false;
        if (workspaceName != null ? !workspaceName.equals(workspace.workspaceName) : workspace.workspaceName != null)
            return false;
        if (ownerGroupId != null ? !ownerGroupId.equals(workspace.ownerGroupId) : workspace.ownerGroupId != null)
            return false;
        if (environments != null ? !environments.equals(workspace.environments) : workspace.environments != null)
            return false;
        return sourceRepositories != null ? sourceRepositories.equals(workspace.sourceRepositories) : workspace.sourceRepositories == null;
    }

    @Override
    public int hashCode() {
        int result = workspaceId;
        result = 31 * result + (workspaceName != null ? workspaceName.hashCode() : 0);
        result = 31 * result + (ownerGroupId != null ? ownerGroupId.hashCode() : 0);
        result = 31 * result + (environments != null ? environments.hashCode() : 0);
        result = 31 * result + (sourceRepositories != null ? sourceRepositories.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "workspaceId=" + workspaceId +
                ", workspaceName='" + workspaceName + '\'' +
                ", ownerGroupId='" + ownerGroupId + '\'' +
                ", environments=" + environments +
                ", sourceRepositories=" + sourceRepositories +
                '}';
    }

    @Override
    public boolean linkChild(Component component) throws UnsupportedOperationException {
        if(component instanceof Environment){
            ((Environment) component).linkParent(this);
        } else if( component instanceof SourceRepository){
            ((SourceRepository) component).linkParent(this);
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public boolean unlinkChild(Component component) throws UnsupportedOperationException {
        if(component instanceof Environment){
//            this.environments.remove((Environment) component); //this removes the child from DB as well
            ((Environment) component).unlinkParent();
        } else if( component instanceof SourceRepository){
//            this.sourceRepositories.remove((SourceRepository) component);
            ((SourceRepository) component).unlinkParent();
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public String isOfType() {
        return Workspace.class.getCanonicalName();
    }

}
