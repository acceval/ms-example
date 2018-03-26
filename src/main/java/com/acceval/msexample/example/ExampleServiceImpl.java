package com.acceval.msexample.example;

import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.acceval.core.repository.QueryResult;
import com.acceval.msexample.type.TypeRepository;
import lombok.RequiredArgsConstructor;

import static org.apache.commons.collections4.IterableUtils.toList;

/**
 * Example service implementation
 *
 * @author Julian
 */
@Service
@RequiredArgsConstructor
public class ExampleServiceImpl implements ExampleService {
	private final ExampleRepository repository;
	private final TypeRepository typeRepository;

	@Override
	public QueryResult query(MultiValueMap<String, String> mapParam) {
		return repository.queryByMapParam(mapParam);
	}

	@Override
	public ExampleDTO addExample(ExampleDTO dto) {
		Example example = new Example();
		example = mergeDTO(example, dto);
		return ExampleDTO.mapToDTO(repository.save(example));
	}

	private Example mergeDTO(Example example, ExampleDTO dto) {
		example.setName(dto.getName());
		example.setDate(dto.getDate());

		example.setType(typeRepository.findById(dto.getType()).orElse(null));
		example.setRadioType(typeRepository.findById(dto.getRadioType()).orElse(null));

		example.setType2(toList(typeRepository.findAllById(dto.getType2())));
		example.setCheckboxType(toList(typeRepository.findAllById(dto.getCheckboxType())));

		return example;
	}
}
