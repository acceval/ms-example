package com.acceval.msexample.example;

import com.acceval.core.repository.QueryResult;
import com.acceval.msexample.type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.IterableUtils.toList;

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
		return repository.findById(id).map(ExampleDTO::mapToDTO);
	}

	@Override
	public void deleteExamples(long... id) {
		Iterable<Example> examples = repository.findAllById(Arrays.stream(id).boxed().collect(Collectors.toList()));

		repository.deleteAll(examples);
	}

	private Example mergeDTO(Example example, ExampleDTO dto) {
		example.setName(dto.getName());
		example.setDate(dto.getDate());

		example.setType(typeRepository.findById(dto.getType()).orElse(null));
		example.setRadioType(typeRepository.findById(dto.getRadioType()).orElse(null));

		example.setType2(toList(typeRepository.findAllById(dto.getType2())));
		example.setCheckboxType(toList(typeRepository.findAllById(dto.getCheckboxType())));

		example.setCheckbox(dto.isCheckbox());

		example.setAutocomplete(typeRepository.findById(dto.getAutocomplete()).orElse(null));

		return example;
	}
}
