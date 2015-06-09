package com.cs.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.cs.exception.UndefinedPropertyException;

public class Props {
	final static Logger log = Logger.getLogger(Props.class);
	private Properties props;

	private Props(){
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
		private static final Props INSTANCE = new Props(); 
	}

	public static Props getInstance(){
		return LazyHolder.INSTANCE;
	}

	public Properties props(){
		return props;
	}

	public String get(String key){
		return props.getProperty(key);
	}

	public String get(String key, String defaultValue) {
		if (containsKey(key)) {
			return get(key);
		} else {
			return defaultValue;
		}
	}

	public long getLong(String name) {
		if (containsKey(name)) {
			return Long.parseLong(get(name));
		} else {
			throw new UndefinedPropertyException("Missing required property '" + name+ "'");
		}
	}

	public int getInt(String name, int defaultValue) {
		if (containsKey(name)) {
			return Integer.parseInt(get(name).trim());
		} else {
			return defaultValue;
		}
	}


	public int getInt(String name) {
		if (containsKey(name)) {
			return Integer.parseInt(get(name).trim());
		} else {
			throw new UndefinedPropertyException("Missing required property '" + name+ "'");
		}
	}

	public double getDouble(String name, double defaultValue) {
		if (containsKey(name)) {
			return Double.parseDouble(get(name).trim());
		} else {
			return defaultValue;
		}
	}

	public double getDouble(String name) {
		if (containsKey(name)) {
			return Double.parseDouble(get(name).trim());
		} else {
			throw new UndefinedPropertyException("Missing required property '" + name+ "'");
		}
	}
	public boolean containsKey(Object k) {
		return props.containsKey(k);	        
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
		return get("es.index.name");
	}

	public String getType(){
		return get("es.catalog.type");
	}

}
