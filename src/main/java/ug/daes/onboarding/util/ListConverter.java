package ug.daes.onboarding.util;

import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Converter
public class ListConverter implements AttributeConverter<List, String> {
	 
		ObjectMapper objectMapper = new ObjectMapper();
	
	 
	    @Override
		public String convertToDatabaseColumn(List list) {
			
			String listJson;
	        try {
	        	listJson = objectMapper.writeValueAsString(list);
	        } catch (final Exception e) {
	           return null;
	        }

			return listJson;
		}

		@Override
		public List convertToEntityAttribute(String listJson) {

	        try {
	        	listJson = listJson.trim();
				return objectMapper.readValue(listJson, List.class);
			}catch (final Exception e) {
	           return null;
	        }
		}
	 
	}