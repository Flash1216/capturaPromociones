package com.portal.app.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "CAP_REG_PROM")
public class RegPromGetDto implements Serializable 
{
	
	private static final long serialVersionUID = 1L;
	//							
	@Id
	@SequenceGenerator(name="SEQ_CAP_REG_PROM_GEN", sequenceName="SEQ_CAP_REG_PROM") 
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_CAP_REG_PROM_GEN")
	@Column
	private Long 	cap_id_n;	
	
	@Column
	private String	cap_nom_str;
	
	@Column
	private Long	id_art_n;
	
	@Column
	private String	cap_descini_str;
	
	@Column
	private String 	cap_descfin_str;
	
	@Column
	private String	cap_est_str;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy", locale = "en")
	private Date cap_fecproini_dt;
	
	@Column
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy", locale = "en")
	private Date cap_fecprofin_dt;

	@Column
	private String	cap_file_str;
	
	@Column
	private String 	cap_ext_str;

	public Long getCap_id_n() {
		return cap_id_n;
	}

	public void setCap_id_n(Long cap_id_n) {
		this.cap_id_n = cap_id_n;
	}

	public String getCap_nom_str() {
		return cap_nom_str;
	}

	public void setCap_nom_str(String cap_nom_str) {
		this.cap_nom_str = cap_nom_str;
	}

	public Long getId_art_n() {
		return id_art_n;
	}

	public void setId_art_n(Long id_art_n) {
		this.id_art_n = id_art_n;
	}

	public String getCap_descini_str() {
		return cap_descini_str;
	}

	public void setCap_descini_str(String cap_descini_str) {
		this.cap_descini_str = cap_descini_str;
	}

	public String getCap_descfin_str() {
		return cap_descfin_str;
	}

	public void setCap_descfin_str(String cap_descfin_str) {
		this.cap_descfin_str = cap_descfin_str;
	}

	public Date getCap_fecproini_dt() {
		return cap_fecproini_dt;
	}

	public void setCap_fecproini_dt(Date cap_fecproini_dt) {
		this.cap_fecproini_dt = cap_fecproini_dt;
	}

	public Date getCap_fecprofin_dt() {
		return cap_fecprofin_dt;
	}

	public void setCap_fecprofin_dt(Date cap_fecprofin_dt) {
		this.cap_fecprofin_dt = cap_fecprofin_dt;
	}

	public String getCap_file_str() {
		return cap_file_str;
	}

	public void setCap_file_str(String cap_file_str) {
		this.cap_file_str = cap_file_str;
	}

	public String getCap_ext_str() {
		return cap_ext_str;
	}

	public void setCap_ext_str(String cap_ext_str) {
		this.cap_ext_str = cap_ext_str;
	}

	public String getCap_est_str() {
		return cap_est_str;
	}

	public void setCap_est_str(String cap_est_str) {
		this.cap_est_str = cap_est_str;
	}
	
}
