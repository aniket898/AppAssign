package com.aniket.homework.componentservice.service;

import com.aniket.homework.componentservice.model.Owner;
import com.aniket.homework.componentservice.model.OwnerGroup;
import com.aniket.homework.componentservice.service.DirectoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    private static final Logger logger = LoggerFactory.getLogger(DirectoryServiceImpl.class);
    private final RestTemplate restTemplate;

    public DirectoryServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    @Async("DirectoryServiceTaskExecutor")
    public CompletableFuture<OwnerGroup> getOwnerGroupsById(String ownerGroupId) throws InterruptedException {
        logger.info("Looking up " + ownerGroupId);
        //String url = String.format("https://localhost:8080/ownerservice/v1/ownergroup/%d", ownerGroupId);
        //OwnerGroup ownerGroup = restTemplate.getForObject(url, OwnerGroup.class);
        List<Owner> ownerList= new ArrayList<>();
        ownerList.add(new Owner(1, "Owner 1", "owner1@gmail.com"));
        ownerList.add(new Owner(2, "Owner 2", "owner2@gmail.com"));
        OwnerGroup ownerGroup = new OwnerGroup(ownerGroupId, ownerList);
        return CompletableFuture.completedFuture(ownerGroup);
    }
}
