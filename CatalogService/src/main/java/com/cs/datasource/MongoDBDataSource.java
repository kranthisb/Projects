package com.cs.datasource;

import org.apache.log4j.Logger;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDBDataSource  extends DataSource {
	final static Logger log = Logger.getLogger(ESDataSource.class);

	private static MongoDBDataSource mongoDBDataSource;
	private MongoClient mongoClient;
	private MongoDatabase mongoDB;
	
	public MongoDatabase mongoDB() {
		return mongoDB;
	}

	public String name(){
		return "MongoDB";
	}
	private MongoDBDataSource(){}
	
	public static MongoDBDataSource getInstance(){
		if(mongoDBDataSource == null){
			mongoDBDataSource = new MongoDBDataSource();
		}
		return mongoDBDataSource;
	}
	
	public void connect(){
		mongoClient = new MongoClient( "localhost" , 27017 );
		mongoDB = mongoClient.getDatabase("store");
		log.info("Connected to MongoDB :"+mongoDB);
	}
	
	public void close(){
		if(mongoClient != null)  {
			mongoClient.close();
			log.info("Successfully closed MongoDB conection");
		}
	}

	public void monitorDataSourceHealth(){ 
		byte status = 0;
		try {
			// TODO : pingDB to check if its accessible..
		} catch (Exception e) {
			log.info("Error while retrieving MongoDB status : "+e.getMessage());
			e.printStackTrace();
		}
		//TODO :  Add more conditions to determine if Cluster is in good health...
		if(status != 0){ //Not Green
			log.error("Unable to reach MongoDB. Please check.");
		}
	}	
}
