package com.aniket.homework.componentservice.model;

import com.aniket.homework.componentservice.service.IParentLinkable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Database", catalog = "component_schema")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Database extends Component implements Serializable, IParentLinkable<Component> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="DatabaseId")
    private int databaseId ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EnvironmentId")
    @JsonBackReference
    private Environment environment;

    @Column(name = "DatabaseName")
    private String databaseName;

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Database database = (Database) o;

        if (databaseId != database.databaseId) return false;
        return databaseName != null ? databaseName.equals(database.databaseName) : database.databaseName == null;
    }

    @Override
    public int hashCode() {
        int result = databaseId;
        result = 31 * result + (databaseName != null ? databaseName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Database{" +
                "databaseId=" + databaseId +
//                ", environment=" + environment.getEnvironmentName() +
                ", databaseName='" + databaseName + '\'' +
                '}';
    }

    @Override
    public boolean linkParent(Component component) throws UnsupportedOperationException {
        unlinkParent();
        if(component instanceof Environment) {
            this.setEnvironment((Environment) component);
        } else {
            throw new UnsupportedOperationException();
        }
        return true;
    }

    @Override
    public boolean unlinkParent() throws UnsupportedOperationException {
        this.setEnvironment(null);
        return true;
    }
//    public boolean unlinkParent(Component component) throws UnsupportedOperationException {
//        if(component instanceof Environment) {
//            this.setEnvironment(null);
//        } else {
//            throw new UnsupportedOperationException();
//        }
//        return true;
//    }

    @Override
    public String isOfType() {
        return Database.class.getCanonicalName();
    }
}
