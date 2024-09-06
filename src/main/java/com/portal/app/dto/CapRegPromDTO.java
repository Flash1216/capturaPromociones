package com.portal.app.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CAP_REG_PROM")
public class CapRegPromDTO implements Serializable
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
	private String	cap_desc_str;
	
	@Column
	private Long	id_art_n;
	
	@Column
	private String cap_fecini_dt;
	
	@Column
	private String cap_fecfin_dt;
	
	@Column
	private String	cap_descini_str;
	
	@Column
	private String 	cap_descfin_str;
	
	@Column
	private String cap_fecreg_dt;
	
	@Column
	private String cap_fecproini_dt;
	
	@Column
	private String cap_fecprofin_dt;

	@Column
	private Long	id_usr_n;
	
	@Column
	private String	cap_est_str;
	
	@Column
	private String	cap_file_str;
	
	@Column
	private String 	cap_ext_str;
	
	@Column
	private String cap_descest_str;
	
	public CapRegPromDTO() 
	{
		
	}

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

	public String getCap_desc_str() {
		return cap_desc_str;
	}

	public void setCap_desc_str(String cap_desc_str) {
		this.cap_desc_str = cap_desc_str;
	}

	public Long getId_art_n() {
		return id_art_n;
	}

	public void setId_art_n(Long id_art_n) {
		this.id_art_n = id_art_n;
	}

	public String getCap_fecini_dt() {
		return cap_fecini_dt;
	}

	public void setCap_fecini_dt(String cap_fecini_dt) {
		this.cap_fecini_dt = cap_fecini_dt;
	}

	public String getCap_fecfin_dt() {
		return cap_fecfin_dt;
	}

	public void setCap_fecfin_dt(String cap_fecfin_dt) {
		this.cap_fecfin_dt = cap_fecfin_dt;
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

	public String getCap_fecreg_dt() {
		return cap_fecreg_dt;
	}

	public void setCap_fecreg_dt(String cap_fecreg_dt) {
		this.cap_fecreg_dt = cap_fecreg_dt;
	}

	public String getCap_fecproini_dt() {
		return cap_fecproini_dt;
	}

	public void setCap_fecproini_dt(String cap_fecproini_dt) {
		this.cap_fecproini_dt = cap_fecproini_dt;
	}

	public String getCap_fecprofin_dt() {
		return cap_fecprofin_dt;
	}

	public void setCap_fecprofin_dt(String cap_fecprofin_dt) {
		this.cap_fecprofin_dt = cap_fecprofin_dt;
	}

	public Long getId_usr_n() {
		return id_usr_n;
	}

	public void setId_usr_n(Long id_usr_n) {
		this.id_usr_n = id_usr_n;
	}

	public String getCap_est_str() {
		return cap_est_str;
	}

	public void setCap_est_str(String cap_est_str) {
		this.cap_est_str = cap_est_str;
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

	public String getCap_descest_str() {
		return cap_descest_str;
	}

	public void setCap_descest_str(String cap_descest_str) {
		this.cap_descest_str = cap_descest_str;
	}
		
}
