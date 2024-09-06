package com.portal.app.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.portal.app.dao.CapDao;
import com.portal.app.request.RegistrarPromocionesRequest;
import com.portal.app.response.RegistrarPromocionesResponse;
import com.portal.app.service.CapService;
import com.portal.app.util.Constants;

@Service
public class CapServiceImpl implements CapService {
	
	private static final Logger log = LoggerFactory.getLogger(CapServiceImpl.class);
	
	@Autowired private CapDao dao;
	

	@Override
	public RegistrarPromocionesResponse getId(RegistrarPromocionesRequest request) {
		// TODO Auto-generated method stub
		dao.getRegister(request);
		return null;
	}

	@Override
	public RegistrarPromocionesResponse getFindProm(RegistrarPromocionesRequest request) {
		RegistrarPromocionesResponse response = new RegistrarPromocionesResponse();
		
		log.debug("Response: " + new Gson().toJson(request));
		
		try
		{
			response.setCapRegProm(dao.getRetrieveId(request));			
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			response.setStatus(Constants.ERROR);
			response.setMessage(e.getMessage());
		}
		
		return response;
	}

	@Override
	public String job() {
		// TODO Auto-generated method stub
		
		return "Se encontraron 20 registros";
	}

}
