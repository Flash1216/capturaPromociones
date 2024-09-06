package com.portal.app.request;

import java.util.List;

import com.portal.app.dto.CapRegPromDTO;

public class RegistrarPromocionesRequest extends Request 
{
	private static final long serialVersionUID = 1L;
	
	private String usCvePstr;
	private CapRegPromDTO capRegPromDto;
	private List<CapRegPromDTO> listCapRegProm;
	
	//Propiedades para Busqueda de Promociones
	private Long	cap_id_n;
	private String	cap_nom_str;
	private String	cap_est_str;
	
	public RegistrarPromocionesRequest () {}
	
	public RegistrarPromocionesRequest(Long cap_id_n, String cap_nom_str, String cap_est_str) {
		this.cap_id_n = cap_id_n;
		this.cap_nom_str = cap_nom_str;
		this.cap_est_str = cap_est_str;
	}
	
	public String getUsCvePstr() {
		return usCvePstr;
	}
	public void setUsCvePstr(String usCvePstr) {
		this.usCvePstr = usCvePstr;
	}
	public CapRegPromDTO getCapRegPromDto() {
		return capRegPromDto;
	}
	public void setCapRegPromDto(CapRegPromDTO capRegPromDto) {
		this.capRegPromDto = capRegPromDto;
	}
	public List<CapRegPromDTO> getListCapRegProm() {
		return listCapRegProm;
	}
	public void setListCapRegProm(List<CapRegPromDTO> listCapRegProm) {
		this.listCapRegProm = listCapRegProm;
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

	public String getCap_est_str() {
		return cap_est_str;
	}

	public void setCap_est_str(String cap_est_str) {
		this.cap_est_str = cap_est_str;
	}
	
}
