package com.portal.app.response;

import java.util.List;

import com.portal.app.dto.CapRegPromDTO;
import com.portal.app.dto.PromoRegDto;
import com.portal.app.dto.RegPromGetDto;

public class RegistrarPromocionesResponse extends Response 
{
	private static final long serialVersionUID = 1L;
	private String 			error;
	private List<CapRegPromDTO> capRegProm;
	private List<PromoRegDto> promRegDto;
	private List<RegPromGetDto> promDtoGet;
	
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
	public List<PromoRegDto> getPromRegDto() {
		return promRegDto;
	}
	public void setPromRegDto(List<PromoRegDto> promRegDto) {
		this.promRegDto = promRegDto;
	}
	public List<RegPromGetDto> getPromDtoGet() {
		return promDtoGet;
	}
	public void setPromDtoGet(List<RegPromGetDto> promDtoGet) {
		this.promDtoGet = promDtoGet;
	}	

}
