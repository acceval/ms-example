package com.acceval.msexample.example;

import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;

/**
 * Example service
 *
 * @author Julian
 */
public interface ExampleService {
	QueryResult query(MultiValueMap<String, String> mapParam);

	Example addExample(Example example);
}
