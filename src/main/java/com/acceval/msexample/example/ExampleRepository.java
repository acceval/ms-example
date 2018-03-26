package com.acceval.msexample.example;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.acceval.core.repository.BaseRepository;

/**
 * Example repository interface
 *
 * @author Julian
 */
@Repository
public interface ExampleRepository extends CrudRepository<Example, Long>, BaseRepository {
}
