package com.portal.app.service;

import com.portal.app.dto.ConsecDto;
import com.portal.app.request.RegistrarPromocionesRequest;
import com.portal.app.response.RegistrarPromocionesResponse;

public interface CapService {
	
	public RegistrarPromocionesResponse getId(RegistrarPromocionesRequest request);
	
	public RegistrarPromocionesResponse getFindProm(RegistrarPromocionesRequest request);
	
	public String job();
	
	public String taskActualizaInfo();
	
	public RegistrarPromocionesResponse getPromosActivo();
	
	public RegistrarPromocionesResponse setPromos(RegistrarPromocionesRequest request);
	
	public ConsecDto getConsecutive();
}
