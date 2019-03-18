package com.aniket.homework.componentservice.dao;

import com.aniket.homework.componentservice.model.Database;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DatabaseRepository extends CrudRepository<Database, Integer>{

//    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
//    Database findByDatabaseId(@Param("DatabaseId") int databaseId);
//
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation= Isolation.READ_UNCOMMITTED)
    List<Database> findAll();
}
