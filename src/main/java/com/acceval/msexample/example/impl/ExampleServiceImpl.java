package com.acceval.msexample.example.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;
import com.acceval.msexample.example.Example;
import com.acceval.msexample.example.ExampleRepository;
import com.acceval.msexample.example.ExampleService;
import lombok.RequiredArgsConstructor;

/**
 * Example service implementation
 *
 * @author Julian
 */
@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {
	private final ExampleRepository repository;

	@Override
	public QueryResult query(MultiValueMap<String, String> mapParam) {
		return repository.queryByMapParam(mapParam);
	}

	@Override
	public Example addExample(Example example) {
		return repository.save(example);
	}
}
