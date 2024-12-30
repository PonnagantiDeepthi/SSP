package ug.daes.onboarding.service.iface;

import java.text.ParseException;
import java.util.Date;

import ug.daes.onboarding.dto.NiraApiLogDto;

public interface LogModelServiceIface {
	
	public void setLogModel(Boolean response,String Identifier, String geoLocation,
							String serviceName,String correlationID,String totalTime, 
							Date startTime,Date endTime,String otpStatus) throws ParseException;
	
	public void setLogModelNiraApi(NiraApiLogDto niraApiLogDto) throws ParseException;

}
