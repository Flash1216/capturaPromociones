package com.portal.app.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.portal.app.dao.CapDao;
import com.portal.app.dao.TaskPromoDao;
import com.portal.app.dto.PromoRegDto;
import com.portal.app.request.RegistrarPromocionesRequest;
import com.portal.app.response.RegistrarPromocionesResponse;
import com.portal.app.service.CapService;
import com.portal.app.util.Constants;

@Service
public class CapServiceImpl implements CapService {
	
	private static final Logger log = LoggerFactory.getLogger(CapServiceImpl.class);
	
	@Autowired private CapDao dao;
	@Autowired private TaskPromoDao taskDao;
	

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
		log.info("taskTest Service");
		return "Se encontraron 20 registros";
	}

	@Override
	public String taskActualizaInfo() {
		@SuppressWarnings("unused")
		List<PromoRegDto> response = new ArrayList<>();
		response = taskDao.taskPromActiva();
		response = taskDao.taskPromExpira();
		return "Informacion Actualizada";
	}

	@Override
	public RegistrarPromocionesResponse getPromosActivo() {
		RegistrarPromocionesResponse responseProm = new RegistrarPromocionesResponse();
		try
		{
			responseProm.setPromDtoGet(dao.getPromoActivas());			
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			responseProm.setStatus(Constants.ERROR);
			responseProm.setMessage(e.getMessage());
		}
		
		return responseProm;
	}

}
