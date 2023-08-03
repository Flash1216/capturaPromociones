package com.portal.app.service;

import com.portal.app.request.AppRequest;
import com.portal.app.response.AppResponse;

public interface AppService 
{
	AppResponse testConexion(AppRequest request);
}
