package com.lee.core.web.mvc;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext sc) throws ServletException {
		// setting Character set for jvm
		System.setProperty("file.encoding", "UTF-8");
		
		/**
		 * servlet init
		 */
		// create the app context belong to servlet.
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");
		// adding dispatcher servlet for current servlet context.
		ServletRegistration.Dynamic dispatcherServlet = sc.addServlet("dispatcher", new DispatcherServlet(appContext));
		// index of start up.
		dispatcherServlet.setLoadOnStartup(1);
		// servlet mapping setting.
		dispatcherServlet.addMapping("/main");
		
		
		
		/**
		 * spring application context init
		 */
		// Create the 'root' Spring application context
		AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(ApplicationContextConfig.class);
		// Manage the lifecycle of the root application context
		sc.addListener(new ContextLoaderListener(rootContext));
	}
}
