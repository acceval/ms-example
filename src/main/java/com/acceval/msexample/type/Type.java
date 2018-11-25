package com.acceval.msexample.type;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Type class definition.
 *
 * @author Julian
 */
@Entity
public class Type {
	@Id
	private long id = 0;
	private String label;
	private String value;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}
