package com.acceval.msexample.type;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Type
 *
 * @author Julian
 */
@Repository
public interface TypeRepository extends CrudRepository<Type, Long> {
}
