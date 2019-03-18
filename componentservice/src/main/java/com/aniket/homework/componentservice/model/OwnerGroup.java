package com.aniket.homework.componentservice.model;

import java.io.Serializable;
import java.util.List;

public class OwnerGroup implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ownerGroupId;
    private List<Owner> owners;

    public OwnerGroup(int ownerGroupId, List<Owner> owners) {
        this.ownerGroupId = ownerGroupId;
        this.owners = owners;
    }

    public int getOwnerGroupId() {
        return ownerGroupId;
    }

    public void setOwnerGroupId(int ownerGroupId) {
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

        if (ownerGroupId != that.ownerGroupId) return false;
        return owners != null ? owners.equals(that.owners) : that.owners == null;
    }

    @Override
    public int hashCode() {
        int result = ownerGroupId;
        result = 31 * result + (owners != null ? owners.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OwnerGroup{" +
                "ownerGroupId=" + ownerGroupId +
                ", owners=" + owners +
                '}';
    }
}
