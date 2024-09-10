package com.portal.app.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.portal.app.dao.TaskPromoDao;
import com.portal.app.dto.CapRegPromDTO;
import com.portal.app.dto.PromoRegDto;

@SuppressWarnings("unchecked")
@Repository
@Transactional(value = "transactionManager", readOnly=true,rollbackFor = Exception.class)
public class TaskPromoDaoImpl implements TaskPromoDao 
{
	private static final Logger log = LoggerFactory.getLogger(TaskPromoDaoImpl.class);
	SimpleDateFormat sfd = new SimpleDateFormat("dd/MMM/yyyy");
	@Autowired	private SessionFactory 	session;
	@SuppressWarnings("unused")
	@Autowired	private Environment		env;
	String estatusRegistrado = "R";
	String estatusActivo = "A";
	String estatusExpirado = "E";
	String date = sfd.format(new Date());
	
	
	@Override
	public String taskPromos() 
	{
		// TODO Auto-generated method stub
		log.debug("Tarea Programada");
		
		try {
			Criteria crit = session.getCurrentSession().createCriteria(CapRegPromDTO.class);
			crit.add(Restrictions.eq("cap_est_str", estatusActivo));
			if(date != null) {
				crit.add(Restrictions.ge("cap_fecproini_dt", date));
			}
			if (date != null) {
				crit.add(Restrictions.le("cap_fecprofin_dt", date));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return "Test";
	}

	//Implementacion para activar las promociones que se encuenten en R y que la fecha de publicacion y la fecha de publicacion fin este dentro de la fecha de busqueda.
	@Override
	@Transactional(readOnly = false)
	public List<PromoRegDto> taskPromActiva() {
		// TODO Auto-generated method stub
		List<PromoRegDto> result = new ArrayList<>();
		try {
			Criteria crit = session.getCurrentSession().createCriteria(PromoRegDto.class);
			crit.add(Restrictions.eq("cap_est_str", estatusRegistrado));
			if(date != null) {
				crit.add(Restrictions.le("cap_fecproini_dt", new Date()));
				crit.add(Restrictions.ge("cap_fecprofin_dt", new Date()));
			}			
			result = crit.list();			
			log.info("updatePromocionesActivas find: " + new Gson().toJson(result));
			if(!result.isEmpty()) {
				for (PromoRegDto register :result) {
					register.setCap_est_str(estatusActivo);
					session.getCurrentSession().update(register);					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}		
		return result;
	}

	
	@Override
	@Transactional(readOnly = false)
	public List<PromoRegDto> taskPromExpira() {
		// TODO Auto-generated method stub
		List<PromoRegDto> result = new ArrayList<>();
		try {
			Criteria crit = session.getCurrentSession().createCriteria(PromoRegDto.class);
			crit.add(Restrictions.eq("cap_est_str", estatusActivo));
			if(date != null) {
				crit.add(Restrictions.le("cap_fecprofin_dt", new Date()));
			}			
			result = crit.list();			
			log.info("updatePromocionesActivas find: " + new Gson().toJson(result));
			if(!result.isEmpty()) {
				for (PromoRegDto register :result) {
					register.setCap_est_str(estatusExpirado);
					session.getCurrentSession().update(register);					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}		
		return result;
	}
	
}
