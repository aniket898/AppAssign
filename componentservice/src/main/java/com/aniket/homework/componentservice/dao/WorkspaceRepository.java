package com.aniket.homework.componentservice.dao;

import com.aniket.homework.componentservice.model.Workspace;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WorkspaceRepository extends CrudRepository<Workspace, Integer>{

//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
//    Workspace findByWorkspaceId(@Param("WorkspaceId") int workspaceId);
//
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
    List<Workspace> findAll();
}
