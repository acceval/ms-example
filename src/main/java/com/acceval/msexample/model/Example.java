package com.acceval.msexample.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Example data class
 *
 * @author Julian
 */
@Entity
public class Example {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id = 0;
	private String name = "";
	private LocalDate date = LocalDate.MIN;

	//test
	
	@ManyToOne
	private Type type;

	@ManyToOne
	private Type radioType;

	@ManyToMany
	@JoinTable
	private List<Type> type2 = new ArrayList<>();

	@ManyToMany
	@JoinTable
	private List<Type> checkboxType = new ArrayList<>();

	@ManyToOne
	private Type autocomplete;

	private boolean checkbox;

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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getRadioType() {
		return radioType;
	}

	public void setRadioType(Type radioType) {
		this.radioType = radioType;
	}

	public List<Type> getType2() {
		return type2;
	}

	public void setType2(List<Type> type2) {
		this.type2 = type2;
	}

	public List<Type> getCheckboxType() {
		return checkboxType;
	}

	public void setCheckboxType(List<Type> checkboxType) {
		this.checkboxType = checkboxType;
	}

	public Type getAutocomplete() {
		return autocomplete;
	}

	public void setAutocomplete(Type autocomplete) {
		this.autocomplete = autocomplete;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}
	
	
}
