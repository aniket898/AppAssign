package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.model.OwnerGroup;

import java.util.List;

public interface DirectoryService {

    List<OwnerGroup> getOwnerGroupsById(int ownerGroupId);
}
