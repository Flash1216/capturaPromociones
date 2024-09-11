package com.portal.app.response;

import java.util.List;

import com.portal.app.dto.CapRegPromDTO;
import com.portal.app.dto.ConsecDto;
import com.portal.app.dto.NewRegCapDto;
import com.portal.app.dto.PromoRegDto;
import com.portal.app.dto.RegPromGetDto;

public class RegistrarPromocionesResponse extends Response 
{
	private static final long serialVersionUID = 1L;
	private String 			error;
	private List<CapRegPromDTO> capRegProm;
	private List<PromoRegDto> promRegDto;
	private List<RegPromGetDto> promDtoGet;
	private ConsecDto			consecutivo;
	private int		consecId;
	private List<NewRegCapDto> newRegDto;
	
	private String fileName;
	
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ConsecDto getConsecutivo() {
		return consecutivo;
	}
	public void setConsecutivo(ConsecDto consecutivo) {
		this.consecutivo = consecutivo;
	}
	public int getConsecId() {
		return consecId;
	}
	public void setConsecId(int consecId) {
		this.consecId = consecId;
	}
	public List<NewRegCapDto> getNewRegDto() {
		return newRegDto;
	}
	public void setNewRegDto(List<NewRegCapDto> newRegDto) {
		this.newRegDto = newRegDto;
	}
	
	
}
