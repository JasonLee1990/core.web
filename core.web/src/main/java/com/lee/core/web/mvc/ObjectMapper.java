package com.lee.core.web.mvc;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;



public class ObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {

	private static final long serialVersionUID = 1L;

	public ObjectMapper() {
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		setVisibilityChecker(getVisibilityChecker().with(JsonAutoDetect.Visibility.NONE));
		setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY);
	}
	
}
