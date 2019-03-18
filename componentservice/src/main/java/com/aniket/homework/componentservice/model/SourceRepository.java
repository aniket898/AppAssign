package com.aniket.homework.componentservice.model;

import com.aniket.homework.componentservice.service.IParentLinkable;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "SourceRepository", catalog = "component_schema")
public class SourceRepository extends Component implements Serializable, IParentLinkable<Component> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="SourceRepositoryId")
    private int sourceRepositoryId ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceId")
    @JsonBackReference
    private Workspace workspace;

    @Column(name = "RepositoryName")
    private String repositoryName;

    public int getSourceRepositoryId() {
        return sourceRepositoryId;
    }

    public void setSourceRepositoryId(int sourceRepositoryId) {
        this.sourceRepositoryId = sourceRepositoryId;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourceRepository that = (SourceRepository) o;

        if (sourceRepositoryId != that.sourceRepositoryId) return false;
        return repositoryName != null ? repositoryName.equals(that.repositoryName) : that.repositoryName == null;
    }

    @Override
    public int hashCode() {
        int result = sourceRepositoryId;
        result = 31 * result + (repositoryName != null ? repositoryName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SourceRepository{" +
                "sourceRepositoryId=" + sourceRepositoryId +
//                ", workspace=" + workspace.getWorkspaceName() +
                ", repositoryName='" + repositoryName + '\'' +
                '}';
    }

    @Override
    public boolean linkParent(Component component) throws UnsupportedOperationException {
        unlinkParent();
        if(component instanceof Workspace) {
            this.setWorkspace((Workspace) component);
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public boolean unlinkParent() throws UnsupportedOperationException {
        this.setWorkspace(null);
        return true;
    }

    @Override
    public String isOfType() {
        return SourceRepository.class.getCanonicalName();
    }
}
