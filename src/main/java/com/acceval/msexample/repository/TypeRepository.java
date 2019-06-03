package com.acceval.msexample.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acceval.msexample.model.Type;

/**
 * Repository for Type
 *
 * @author Julian
 */
@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
}
