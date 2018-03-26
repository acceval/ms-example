package com.acceval.msexample.type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * Type class definition.
 *
 * @author Julian
 */
@Entity
public @Data class Type {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id = 0;
	private String label;
	private String value;
}
