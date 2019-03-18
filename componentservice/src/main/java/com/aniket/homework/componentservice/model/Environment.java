package com.aniket.homework.componentservice.model;

import com.aniket.homework.componentservice.service.IChildLinkable;
import com.aniket.homework.componentservice.service.IParentLinkable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Environment", catalog = "component_schema")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Environment extends Component implements Serializable, IChildLinkable<Component>, IParentLinkable<Component> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="EnvironmentId")
    private int environmentId ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceId")
    @JsonBackReference
    private Workspace workspace;

    @Column(name = "EnvironmentName")
    private String environmentName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "environment", cascade={CascadeType.ALL,CascadeType.PERSIST,CascadeType.MERGE},
            orphanRemoval = true)
    @JsonManagedReference
    private Set<Database> databases;

    public int getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(int environmentId) {
        this.environmentId = environmentId;
    }

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public Set<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(Set<Database> databases) {
        this.databases = databases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Environment that = (Environment) o;

        if (environmentId != that.environmentId) return false;
        if (environmentName != null ? !environmentName.equals(that.environmentName) : that.environmentName != null)
            return false;
        return databases != null ? databases.equals(that.databases) : that.databases == null;
    }

    @Override
    public int hashCode() {
        int result = environmentId;
        result = 31 * result + (environmentName != null ? environmentName.hashCode() : 0);
        result = 31 * result + (databases != null ? databases.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "environmentId=" + environmentId +
//                ", workspace=" + workspace.getWorkspaceName() +
                ", environmentName='" + environmentName + '\'' +
                ", databases=" + databases +
                '}';
    }


    @Override
    public boolean linkChild(Component component) throws UnsupportedOperationException {
        if(component instanceof Database) {
            //this.databases.add((Database) component);
            ((Database) component).linkParent(this);
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public boolean unlinkChild(Component component) throws UnsupportedOperationException {
        if(component instanceof Database){
//            this.databases.remove((Database) component);
            ((Database) component).unlinkParent();
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public boolean linkParent(Component component) throws UnsupportedOperationException{
        unlinkParent();
        if(component instanceof Workspace) {
            this.setWorkspace((Workspace) component);
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public boolean unlinkParent() throws UnsupportedOperationException{
        this.setWorkspace(null);
        return true;
    }
//    public boolean unlinkParent(Component component) throws UnsupportedOperationException{
//        if(component instanceof Workspace) {
//            this.setWorkspace(null);
//        } else {
//            throw new UnsupportedOperationException();
//        }
//        return true;
//    }

    @Override
    public String isOfType() {
        return Environment.class.getCanonicalName();
    }
}
