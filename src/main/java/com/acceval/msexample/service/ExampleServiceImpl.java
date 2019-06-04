package com.acceval.msexample.service;

import static org.apache.commons.collections4.IterableUtils.toList;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;
import com.acceval.msexample.model.Example;
import com.acceval.msexample.model.ExampleDTO;
import com.acceval.msexample.repository.ExampleRepository;
import com.acceval.msexample.repository.TypeRepository;

/**
 * Example service implementation
 *
 * @author Julian
 */
@Service
public class ExampleServiceImpl implements ExampleService {

	@Autowired
	private ExampleRepository repository;
	@Autowired
	private TypeRepository typeRepository;

	@Override
	public QueryResult<ExampleDTO> query(MultiValueMap<String, String> mapParam) {
		QueryResult<Example> queryResult = repository.queryByMapParam(mapParam);

		return new QueryResult<>(queryResult.getTotal(),
				queryResult.getResults().stream().map(ExampleDTO::mapToDTO).collect(Collectors.toList()));
	}

	@Override
	public ExampleDTO addExample(ExampleDTO dto) {
		Example example = new Example();
		example = mergeDTO(example, dto);
		return ExampleDTO.mapToDTO(repository.save(example));
	}

	@Nonnull
	@Override
	public Optional<ExampleDTO> updateExample(long id, ExampleDTO dto) {
		Optional<Example> example = repository.findById(id);
		if (!example.isPresent()) {
			return Optional.empty();
		}

		Example ex = mergeDTO(example.get(), dto);
		return Optional.of(ExampleDTO.mapToDTO(repository.save(ex)));
	}

	@Nonnull
	@Override
	public Optional<ExampleDTO> getExample(long id) {
		return Optional.of(ExampleDTO.mapToDTO(repository.findById(id).get()));
	}

	@Override
	public void deleteExamples(long... id) {
		Iterable<Example> examples = repository.findAllById(Arrays.stream(id).boxed().collect(Collectors.toList()));

		repository.deleteAll(examples);
	}

	private Example mergeDTO(Example example, ExampleDTO dto) {
		example.setName(dto.getName());
		example.setDate(dto.getDate());

		example.setType(typeRepository.findById(dto.getType()).get());
		example.setRadioType(typeRepository.findById(dto.getRadioType()).get());

		example.setType2(toList(typeRepository.findAllById(dto.getType2())));
		example.setCheckboxType(toList(typeRepository.findAllById(dto.getCheckboxType())));

		example.setCheckbox(dto.isCheckbox());

		example.setAutocomplete(typeRepository.findById(dto.getAutocomplete()).get());

		return example;
	}
}
