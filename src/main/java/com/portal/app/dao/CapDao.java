package com.portal.app.dao;

import java.util.List;

import com.portal.app.dto.CapRegPromDTO;
import com.portal.app.request.RegistrarPromocionesRequest;

public interface CapDao {
	
	List<CapRegPromDTO>	getRegister(RegistrarPromocionesRequest request);
	
	List<CapRegPromDTO>	getRetrieveId(RegistrarPromocionesRequest request);
}
