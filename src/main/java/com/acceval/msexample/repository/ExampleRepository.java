package com.acceval.msexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.acceval.core.repository.BaseRepository;
import com.acceval.msexample.model.Example;

/**
 * Example repository interface
 *
 * @author Julian
 */
@Repository
public interface ExampleRepository extends JpaRepository<Example, Long>, BaseRepository {
}
