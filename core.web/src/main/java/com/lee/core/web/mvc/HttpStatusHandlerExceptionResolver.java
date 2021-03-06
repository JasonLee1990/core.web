package com.lee.core.web.mvc;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.lee.core.support.exception.AppException;

public class HttpStatusHandlerExceptionResolver implements HandlerExceptionResolver {

	private Logger logger = LoggerFactory.getLogger(HttpStatusHandlerExceptionResolver.class);
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		Map<String, Object> badRequestReason = new HashMap<String, Object>();
		try {
			if (ex instanceof HttpRequestMethodNotSupportedException) {
				response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			} else if (ex instanceof BindException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				badRequestReason.put("validateError", convertToValidateException((BindException) ex).getErrors());
			} else if (ex instanceof ValidateException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				badRequestReason.put("validateError", ((ValidateException) ex).getErrors());
			} else if (ex instanceof AppException) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				badRequestReason.put("applicationError", ((AppException) ex).getMessage());
			} /*else if (ex instanceof OAuth2Exception) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			} */else {
				logger.error("处理请求时发生异常 " + request.getMethod() + " " + request.getRequestURI(), ex);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			// for bad request
			if (!response.isCommitted() && !badRequestReason.isEmpty()) {
//				ServletUtil.respondObjectAsJson(response, badRequestReason);
			}
		} catch (Throwable throwable) {
			logger.warn("处理响应状态发生异常", throwable);
		}
		
		
		// 如果该方法返回了null，则Spring会继续寻找其他的HandlerExceptionResolver，直到返回了一个ModelAndView对象。
		return new ModelAndView();
	}
	
	private ValidateException convertToValidateException(BindException bindException) {
		BindingResult bindingResult = bindException.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		Map<String, String> errors = new HashMap<String, String>();
		String fieldTypeName;
		String fieldName;
		for (FieldError fe : fieldErrors) {
			fieldName = fe.getField();
			fieldTypeName = convertClassName(bindingResult.getFieldType(fieldName));
			if (!errors.containsKey(fieldName)) {
				errors.put(fieldName, "必须为" + fieldTypeName);
			}
		}
		return new ValidateException(errors);
	}

	private String convertClassName(Class<?> clazz) {
		if (clazz.equals(Integer.class)) {
			return "整数";
		} else if (clazz.equals(Float.class)) {
			return "浮点数";
		} else if (clazz.equals(Double.class)) {
			return "双精度浮点数";
		} else if (clazz.equals(Boolean.class)) {
			return "布尔型";
		} else {
			return clazz.getSimpleName();
		}
	}

}
