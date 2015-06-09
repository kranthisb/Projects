package com.cs.datasource;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.cs.exception.DataSourceUnavailableException;
import com.cs.servlet.listener.AppContext;
import com.cs.util.Utils;

public class DataSourceObserver {
	final static Logger log = Logger.getLogger(DataSourceObserver.class);

	private List<DataSource> dataSources = new ArrayList<DataSource>();
	private static DataSourceObserver dataSourceObserver;

	private DataSourceObserver(){}

	public static DataSourceObserver getInstance(){
		if(dataSourceObserver == null)
			dataSourceObserver = new DataSourceObserver();
		return dataSourceObserver;
	}

	public void registerDataSource(DataSource source){
		if(!dataSources.contains(source)){
			dataSources.add(source);
		}
	}

	public void connectToAllDataSources(){
		String sourceName = null;
		for(DataSource oneSource : dataSources){
			sourceName = oneSource.name();

			try{
				oneSource.connect();
				log.info("Connected to source : "+sourceName);
			}catch(DataSourceUnavailableException e){
				e.printStackTrace();
				Utils.exitApp("Error connecting to Source :"+oneSource.name(), 2);
			}
		}
	}

	public void disConnectFromAllDataSources(){
		String sourceName = null;
		for(DataSource oneSource : dataSources){
			sourceName = oneSource.name();
			oneSource.close();
			log.info("Disconnected from source : "+sourceName);
		}
	}
}
