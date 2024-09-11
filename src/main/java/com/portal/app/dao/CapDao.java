package com.portal.app.dao;

import java.util.List;

import com.portal.app.dto.CapRegPromDTO;
import com.portal.app.dto.ConsecDto;
import com.portal.app.dto.NewRegCapDto;
import com.portal.app.dto.RegPromGetDto;
import com.portal.app.request.RegistrarPromocionesRequest;

public interface CapDao {
	
	List<CapRegPromDTO>	getRegister(RegistrarPromocionesRequest request);
	
	List<CapRegPromDTO>	getRetrieveId(RegistrarPromocionesRequest request);
	
	List<RegPromGetDto> getPromoActivas();
	
	List<NewRegCapDto> setPromos(NewRegCapDto item);
	
	ConsecDto	getConsecutive();
}
