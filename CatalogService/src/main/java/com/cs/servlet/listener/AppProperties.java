package com.cs.servlet.listener;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AppProperties {
	final static Logger log = Logger.getLogger(AppProperties.class);
	private Properties props;

	private AppProperties(){
		String fileName = "config-dev.properties";
		log.debug("Loading Properties file : "+fileName);
		try {
			InputStream in = this.getClass().getClassLoader().getResourceAsStream(fileName);
			props = new Properties();
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class LazyHolder{
		private static final AppProperties INSTANCE = new AppProperties(); 
	}

	public static AppProperties getInstance(){
		return LazyHolder.INSTANCE;
	}

	public Properties props(){
		return props;
	}

	public String getProperty(String key){
		return props.getProperty(key);
	}

	public void printAll(){
		if(props != null){
			Enumeration<?>e = props.propertyNames();
			while(e.hasMoreElements()){
				Object k = e.nextElement();
				Object v = props.getProperty(k.toString());
				log.info(k +" : "+v);
			}

		}
	}

	public String getIndexName(){
		return getProperty("es.index.name");
	}

	public String getType(){
		return getProperty("es.catalog.type");
	}

}
