package com.mozzartbet.gameservice.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import oracle.sql.TIMESTAMP;

public interface BaseEntity {

	public Long getId();
	
	public void setId(Long id);
	
	public Timestamp getCreatedOn();

	public void setCreatedOn(Timestamp createdOn);
	
	public Timestamp getModifiedOn();

	public void setModifiedOn(Timestamp modifiedOn);
	
}
