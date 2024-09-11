package com.portal.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ConsecDto implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	private Long 	cap_id_n;	
}
