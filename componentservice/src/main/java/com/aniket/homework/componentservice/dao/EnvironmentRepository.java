package com.aniket.homework.componentservice.dao;

import com.aniket.homework.componentservice.model.Environment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EnvironmentRepository extends CrudRepository<Environment, Integer>{

//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
//    Environment findByEnvironmentId(@Param("EnvironmentId") int environmentId);
//
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
    List<Environment> findAll();


}
