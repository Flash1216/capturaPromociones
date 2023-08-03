package com.portal.app.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.portal.app.dao.AppDao;
import com.portal.app.request.AppRequest;
import com.portal.app.response.AppResponse;

@Repository
@Transactional(readOnly=true,rollbackFor = Exception.class)
public class AppDaoImpl implements AppDao
{
	private static final Logger log = LoggerFactory.getLogger(AppDaoImpl.class);
	
	@Autowired	private SessionFactory session;
	
	@Override
	public AppResponse test(AppRequest request) 
	{
		AppResponse response = new AppResponse();
		
		log.debug("test dao " + new Gson().toJson(request));
		session.getCurrentSession().createSQLQuery(" SELECT 1 FROM DUAL").uniqueResult();
		response.setMessage("Conexión con BD correcta");
		return response;
	}

}
