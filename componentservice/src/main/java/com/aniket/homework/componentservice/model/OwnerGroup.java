package com.aniket.homework.componentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ownerGroupId;
    private List<Owner> owners;

    public OwnerGroup(String ownerGroupId, List<Owner> owners) {
        this.ownerGroupId = ownerGroupId;
        this.owners = owners;
    }

    public String getOwnerGroupId() {
        return ownerGroupId;
    }

    public void setOwnerGroupId(String ownerGroupId) {
        this.ownerGroupId = ownerGroupId;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnerGroup that = (OwnerGroup) o;

        if (ownerGroupId != null ? !ownerGroupId.equals(that.ownerGroupId) : that.ownerGroupId != null) return false;
        return owners != null ? owners.equals(that.owners) : that.owners == null;
    }

    @Override
    public int hashCode() {
        int result = ownerGroupId != null ? ownerGroupId.hashCode() : 0;
        result = 31 * result + (owners != null ? owners.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OwnerGroup{" +
                "ownerGroupId='" + ownerGroupId + '\'' +
                ", owners=" + owners +
                '}';
    }
}
