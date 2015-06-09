package com.cs.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.cs.datasource.DataSourceObserver;
import com.cs.datasource.ESDataSource;
import com.cs.datasource.MongoDBDataSource;
import com.cs.util.Props;
import com.cs.util.SystemMemoryInfo;
import com.cs.util.Utils;

public class AppContext implements ServletContextListener
{
	final static Logger log = Logger.getLogger(AppContext.class);

	private Props props = null;
	private static AppContext appContext;
	
	public static AppContext getInstance(){
		if(appContext == null){
			appContext = new AppContext();
		}
		return appContext;
	}

	public void contextInitialized( ServletContextEvent sce ){
		props = Props.getInstance();
		props.printAll();
		
		DataSourceObserver dso = DataSourceObserver.getInstance();
		dso.registerDataSource(ESDataSource.getInstance());
		dso.registerDataSource(MongoDBDataSource.getInstance());
		
		connectToDataSource();
		SystemMemoryInfo.init(props.getInt("executor.memCheck.interval", 30));	
		log.debug("Context initialized successfully");
	}

	private void connectToDataSource(){
		DataSourceObserver dso = DataSourceObserver.getInstance();
		dso.connectToAllDataSources();
	}

	public void contextDestroyed( ServletContextEvent sce ){
		log.info("Closing ES & MongoDB Connection");
		DataSourceObserver dso = DataSourceObserver.getInstance();
		dso.disConnectFromAllDataSources();		
		try{
			Utils.logTopMemoryConsumers();
		}catch(Exception e){
			log.error("Error while logging Top Memory consumers.");
		}
		log.info("Shutting down server.. Bbye");
	}
}
