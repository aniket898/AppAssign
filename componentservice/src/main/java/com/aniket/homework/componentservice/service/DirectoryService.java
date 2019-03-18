package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.model.OwnerGroup;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DirectoryService {

    CompletableFuture<OwnerGroup> getOwnerGroupsById(String ownerGroupId) throws InterruptedException;
}
