package com.aniket.homework.componentservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Owner implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ownerId;
    private String ownerName;
    private String ownerEmailId;

    public Owner(int ownerId, String ownerName, String ownerEmailId) {
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.ownerEmailId = ownerEmailId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmailId() {
        return ownerEmailId;
    }

    public void setOwnerEmailId(String ownerEmailId) {
        this.ownerEmailId = ownerEmailId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Owner owner = (Owner) o;

        if (ownerId != owner.ownerId) return false;
        if (ownerName != null ? !ownerName.equals(owner.ownerName) : owner.ownerName != null) return false;
        return ownerEmailId != null ? ownerEmailId.equals(owner.ownerEmailId) : owner.ownerEmailId == null;
    }

    @Override
    public int hashCode() {
        int result = ownerId;
        result = 31 * result + (ownerName != null ? ownerName.hashCode() : 0);
        result = 31 * result + (ownerEmailId != null ? ownerEmailId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "ownerId=" + ownerId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerEmailId='" + ownerEmailId + '\'' +
                '}';
    }
}
