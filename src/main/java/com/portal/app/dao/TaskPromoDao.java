package com.portal.app.dao;

import java.util.List;

import com.portal.app.dto.PromoRegDto;

public interface TaskPromoDao {
	
	String taskPromos();
	List<PromoRegDto> taskPromActiva();
	List<PromoRegDto> taskPromExpira();
	
}
