package com.acceval.msexample.example;

import com.acceval.core.repository.BaseRepository;
import com.acceval.core.repository.BaseRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

/**
 * Example repository implementation
 *
 * @author Julian
 */
public class ExampleRepositoryImpl extends BaseRepositoryImpl implements BaseRepository {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Override
	protected EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	protected Class<?> getTargetClass() {
		return Example.class;
	}
}
