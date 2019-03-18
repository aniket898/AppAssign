package com.aniket.homework.componentservice.model;

import com.aniket.homework.componentservice.service.DirectoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryServiceImpl implements DirectoryService {

    @Override
    public List<OwnerGroup> getOwnerGroupsById(int ownerGroupId) {
        List<OwnerGroup> ownerGroupList= new ArrayList<>();
        List<Owner> ownerList= new ArrayList<>();
        ownerList.add(new Owner(1, "Owner 1", "owner1@gmail.com"));
        ownerList.add(new Owner(2, "Owner 2", "owner2@gmail.com"));
        OwnerGroup ownerGroup = new OwnerGroup(1, ownerList);
        ownerGroupList.add(ownerGroup);
        return ownerGroupList;
    }
}
