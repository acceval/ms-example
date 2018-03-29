package com.acceval.msexample.example;

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

import com.acceval.msexample.type.Type;
import lombok.Data;

/**
 * Example data class
 *
 * @author Julian
 */
@Entity
public @Data class Example {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id = 0;
	private String name = "";
	private LocalDate date = LocalDate.MIN;

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

	private boolean checkbox;
}
