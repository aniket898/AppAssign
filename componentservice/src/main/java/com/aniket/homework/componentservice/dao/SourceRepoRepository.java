package com.aniket.homework.componentservice.dao;

import com.aniket.homework.componentservice.model.SourceRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SourceRepoRepository extends CrudRepository<SourceRepository, Integer>{

//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
//    SourceRepository findBySourceRepositoryId(@Param("SourceRepositoryId") int sourceRepositoryId);
//
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
    List<SourceRepository> findAll();
}
