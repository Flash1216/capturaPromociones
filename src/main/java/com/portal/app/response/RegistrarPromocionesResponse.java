package com.portal.app.response;

import java.util.List;

import com.portal.app.dto.CapRegPromDTO;

public class RegistrarPromocionesResponse extends Response 
{
	private static final long serialVersionUID = 1L;
	private String 			error;
	private List<CapRegPromDTO> capRegProm;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public List<CapRegPromDTO> getCapRegProm() {
		return capRegProm;
	}
	public void setCapRegProm(List<CapRegPromDTO> capRegProm) {
		this.capRegProm = capRegProm;
	}
	
	public RegistrarPromocionesResponse() {}
	
	public RegistrarPromocionesResponse(String encodedData) {
		super(encodedData);
	}	
}
