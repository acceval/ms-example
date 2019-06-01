package com.acceval.msexample.service;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;
import com.acceval.msexample.model.ExampleDTO;

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
