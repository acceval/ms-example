package com.acceval.msexample.example.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import com.acceval.core.repository.BaseRepository;
import com.acceval.core.repository.BaseRepositoryImpl;
import com.acceval.msexample.example.Example;
import lombok.RequiredArgsConstructor;

/**
 * Example repository implementation
 *
 * @author Julian
 */
@RequiredArgsConstructor
public class ExampleRepositoryImpl extends BaseRepositoryImpl implements BaseRepository {
	@PersistenceContext
	private EntityManager entityManager;

	private final EntityManagerFactory entityManagerFactory;

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
