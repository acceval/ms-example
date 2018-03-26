package com.acceval.msexample.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.acceval.msexample.type.Type;
import lombok.Data;

/**
 * Form class for example data object
 *
 * @author Julian
 */
@Data class ExampleDTO {
	private long id = 0;
	private String name = "";
	private LocalDate date = LocalDate.MIN;
	private Long type;
	private Long radioType;
	private List<Long> type2 = new ArrayList<>();
	private List<Long> checkboxType = new ArrayList<>();

	public static ExampleDTO mapToDTO(Example example) {
		ExampleDTO dto = new ExampleDTO();

		dto.id = example.getId();
		dto.name = example.getName();
		dto.date = example.getDate();
		dto.type = example.getType() != null ? example.getType().getId() : 0;
		dto.radioType = example.getRadioType() != null ? example.getRadioType().getId() : 0;
		dto.type2 = example.getType2() != null ? example.getType2().stream().map(Type::getId).collect(Collectors
				.toList()) : Collections.emptyList();
		dto.checkboxType = example.getCheckboxType() != null ? example.getCheckboxType().stream().map(Type::getId).collect(Collectors
				.toList()) : Collections.emptyList();

		return dto;
	}
}
