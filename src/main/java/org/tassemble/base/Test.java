package org.tassemble.base;

import org.tassemble.base.commons.dao.sql.annotations.DataProperty;


public class Test {

	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@DataProperty(column="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
