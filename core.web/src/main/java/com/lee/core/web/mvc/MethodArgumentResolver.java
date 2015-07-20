package com.lee.core.web.mvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lee.core.business.repository.CommonRepository;
import com.lee.system.repository.DepartmentRepository;
import com.lee.system.repository.UserRepository;

public class MethodArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * Manual inject an object to argument of method in controller .
	 */
	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return new Current(context.getBean(UserRepository.class),context.getBean(DepartmentRepository.class),context.getBean(CommonRepository.class));
	}

	/**
	 * Register the parameter defined in above method. 
	 */
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterType().isAssignableFrom(Current.class);
	}

}