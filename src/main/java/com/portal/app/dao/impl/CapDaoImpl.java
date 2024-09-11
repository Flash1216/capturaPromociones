package com.portal.app.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.portal.app.dao.CapDao;
import com.portal.app.dto.CapRegPromDTO;
import com.portal.app.dto.ConsecDto;
import com.portal.app.dto.NewRegCapDto;
import com.portal.app.dto.RegPromGetDto;
import com.portal.app.request.RegistrarPromocionesRequest;

@Repository
@Transactional(value = "transactionManager", readOnly=true,rollbackFor = Exception.class)
public class CapDaoImpl implements CapDao {
	private static final Logger log = LoggerFactory.getLogger(CapDaoImpl.class);
	
	@Autowired	private SessionFactory 	session;
	@SuppressWarnings("unused")
	@Autowired	private Environment		env;
	String statusActivo = "A";
	
	@Override
	public List<CapRegPromDTO> getRegister(RegistrarPromocionesRequest request) {
		// TODO Auto-generated method stub
		
		CapRegPromDTO item = request.getCapRegPromDto();
		
		Criteria crit = session.getCurrentSession().createCriteria(CapRegPromDTO.class);
		crit.add(Restrictions.eq("cap_id_n", item.getCap_id_n()		));
		CapRegPromDTO result = (CapRegPromDTO) crit.list();
		log.debug("Datos: " + new Gson().toJson(result));
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapRegPromDTO> getRetrieveId(RegistrarPromocionesRequest req) 
	{
		log.debug("DAO: " + new Gson().toJson(req));
		
		List<CapRegPromDTO> result = new ArrayList<>(); 
		Transaction tx = null;
		Session sess = null; 
		 
		try { 
			StringBuilder qry = new StringBuilder(); 
			qry.append(" SELECT A.CAP_ID_N, A.CAP_NOM_STR, A.CAP_DESC_STR , A.ID_ART_N, TO_CHAR(A.CAP_FECINI_DT, 'DD/MM/YYYY') CAP_FECINI_DT," ); 
			qry.append(" TO_CHAR(A.CAP_FECFIN_DT , 'DD/MM/YYYY') CAP_FECFIN_DT, A.CAP_DESCINI_STR, A.CAP_DESCFIN_STR, TO_CHAR(A.CAP_FECREG_DT , 'DD/MM/YYYY') CAP_FECREG_DT,"); 
			qry.append(" TO_CHAR(A.CAP_FECPROINI_DT, 'DD/MM/YYYY') CAP_FECPROINI_DT, TO_CHAR(A.CAP_FECPROFIN_DT , 'DD/MM/YYYY') CAP_FECPROFIN_DT, A.ID_USR_N, "); 
			qry.append(" A.CAP_EST_STR, B.CAP_DESC_STR CAP_DESCEST_STR, A.CAP_FILE_STR, A.CAP_EXT_STR "); 
			qry.append(" FROM CAP_REG_PROM A INNER JOIN CAP_ESTATUS B ON ");
			qry.append(" A.CAP_EST_STR = B.CAP_EST_STR WHERE A.CAP_ID_N IS NOT NULL");
			if(req.getCap_id_n() != null) {
				qry.append(" AND A.CAP_ID_N IN (").append(req.getCap_id_n()).append(")"); 
			}
			if(req.getCap_nom_str().length() != 0 ) {
				qry.append(" AND UPPER(A.CAP_NOM_STR) LIKE '%").append(req.getCap_nom_str()).append("%' "); 
			} 
			if(req.getCap_est_str().length() != 0 ) {
				qry.append(" AND A.CAP_EST_STR IN ('").append(req.getCap_est_str()).append("')");
			} 
			qry.append(" ORDER BY A.CAP_FECREG_DT DESC "); log.debug("QRY: " + qry);
			
			log.debug("QRY: ", qry.toString());
			sess = session.openSession(); 
			tx = sess.beginTransaction(); 
			result = sess.createSQLQuery(qry.toString()).addEntity(CapRegPromDTO.class).list();
			 
			tx.commit(); 
		} catch (Exception e) { 
			log.error(e.getMessage(), e); 
		} finally { 
			if(sess != null) { 
				sess.close(); 
			} 
		} 
		
		log.debug("Datos Response: " + new Gson().toJson(result));
		 
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RegPromGetDto> getPromoActivas() 
	{
		Criteria crit = session.getCurrentSession().createCriteria(RegPromGetDto.class);
		crit.add(Restrictions.eq("cap_est_str", statusActivo));
		List<RegPromGetDto> result = new ArrayList<>();
		result = crit.list();
		log.debug("Datos Response: " + new Gson().toJson(result));
		 
		return result;
	}

	@Override
	public ConsecDto getConsecutive() 
	{
		ConsecDto consec = new ConsecDto();
		Transaction tx = null;
		Session sess = null; 
		 
		try { 
			StringBuilder qry = new StringBuilder(); 
			qry.append(" SELECT SEQ_CAP_REG_PROM.NEXTVAL AS cap_id_n FROM DUAL " ); 
			
			log.debug("QRY: ", qry.toString());
			sess = session.openSession(); 
			tx = sess.beginTransaction(); 
			consec = (ConsecDto) sess.createSQLQuery(qry.toString()).addEntity(ConsecDto.class).uniqueResult();
			 
			tx.commit(); 
		} catch (Exception e) { 
			log.error(e.getMessage(), e); 
		} finally { 
			if(sess != null) { 
				sess.close(); 
			} 
		} 
		
		log.debug("Datos Response: " + new Gson().toJson(consec));
		 
		return consec;
	}

	@Override
	@Transactional(readOnly = false)
	public List<NewRegCapDto> setPromos(NewRegCapDto item) 
	{
		List<NewRegCapDto> result = new ArrayList<>();
		log.info("DAO IMPL" + new Gson().toJson(item));
		Date date = new Date();
		String dateRegStr = "";
		String dateIniStr = item.getCap_fecini_dt();
        String dateFinStr = item.getCap_fecfin_dt();
        String datePubIniStr = item.getCap_fecproini_dt();
        String datePubFinStr = item.getCap_fecprofin_dt();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy");
		SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dateRegStr = formatter.format(date);
			datePubIniStr = formatter.format(originalFormat.parse(datePubIniStr));
			datePubFinStr = formatter.format(originalFormat.parse(datePubFinStr));			
			if(dateIniStr != "") {
				dateIniStr = formatter.format(originalFormat.parse(dateIniStr));
			}
			if(dateFinStr != "") {
				dateFinStr = formatter.format(originalFormat.parse(dateFinStr));
			}
			String file = item.getCap_file_str();
			String ext 	= file.substring(file.lastIndexOf(".")+1).toLowerCase();
			String fileName = file.substring(0, file.lastIndexOf(".")).toLowerCase();
			
			@SuppressWarnings("unused")
			Criteria crit = session.getCurrentSession().createCriteria(NewRegCapDto.class);
				item.setCap_id_n(item.getCap_id_n());
				item.setCap_file_str(fileName);
				item.setCap_ext_str(ext);
				item.setCap_fecreg_dt(dateRegStr);
				item.setCap_fecini_dt(dateIniStr);
				item.setCap_fecfin_dt(dateFinStr);
				item.setCap_fecproini_dt(datePubIniStr);
				item.setCap_fecprofin_dt(datePubFinStr);
				item.setCap_est_str("R");
				session.getCurrentSession().save(item);
				//session.getCurrentSession().saveOrUpdate(item);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return result;
	}
	
}
