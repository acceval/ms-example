package com.acceval.msexample.example;

import com.acceval.msexample.type.Type;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Form class for example data object
 *
 * @author Julian
 */
class ExampleDTO {
	private long id = 0;
	private String name = "";
	private LocalDate date = LocalDate.MIN;
	private long type;
	private long radioType;
	private List<Long> type2 = new ArrayList<>();
	private List<Long> checkboxType = new ArrayList<>();
	private boolean checkbox;
	private long autocomplete;

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
		dto.checkbox = example.isCheckbox();
		dto.autocomplete = example.getAutocomplete() != null ? example.getAutocomplete().getId() : 0;

		return dto;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public long getRadioType() {
		return radioType;
	}

	public void setRadioType(long radioType) {
		this.radioType = radioType;
	}

	public List<Long> getType2() {
		return type2;
	}

	public void setType2(List<Long> type2) {
		this.type2 = type2;
	}

	public List<Long> getCheckboxType() {
		return checkboxType;
	}

	public void setCheckboxType(List<Long> checkboxType) {
		this.checkboxType = checkboxType;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public long getAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(long autocomplete) {
		this.autocomplete = autocomplete;
	}
}
