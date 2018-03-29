package com.acceval.msexample.example;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;

/**
 * Example service
 *
 * @author Julian
 */
public interface ExampleService {
	QueryResult query(MultiValueMap<String, String> mapParam);

	ExampleDTO addExample(ExampleDTO dto);

	@Nonnull Optional<ExampleDTO> updateExample(long id, ExampleDTO dto);

	void deleteExamples(long... id);

	@Nonnull Optional<ExampleDTO> getExample(long id);
}
