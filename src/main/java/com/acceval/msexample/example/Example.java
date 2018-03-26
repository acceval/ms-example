package com.acceval.msexample.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	private String type = "";
	private List<String> type2 = new ArrayList<>();

	private String rType = "";
	private List<String> cType = new ArrayList<>();
}
