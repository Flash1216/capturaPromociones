package com.portal.app.service.impl;

import static com.portal.app.util.Constants.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.portal.app.dao.AppDao;
import com.portal.app.request.AppRequest;
import com.portal.app.response.AppResponse;
import com.portal.app.service.AppService;

@Service
public class AppServiceImpl implements AppService 
{
	private static final Logger log = LoggerFactory.getLogger(AppServiceImpl.class);
	
	@Autowired 
	private AppDao dao;
	
	@Override
	public AppResponse testConexion(AppRequest request) 
	{
		log.info("testConexion" + new Gson().toJson(request));
		AppResponse response = new AppResponse();
		try
		{
			response = dao.test(request) ;
		}
		catch (Exception e) 
		{
			log.error(e.getMessage(),e);
			response.setStatus(ERROR);
			response.setMessage(e.getMessage());
		}
		return response;
	}
}
