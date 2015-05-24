package com.cs.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class AppContext implements ServletContextListener
{
	final static Logger log = Logger.getLogger(AppContext.class);

	private static Client esClient;
	private static MongoClient mongoClient;
	private static MongoCollection<Document> catalogs;
	
	private static MongoDatabase mongoDB;

	public void contextInitialized( ServletContextEvent sce ){
		AppProperties props = AppProperties.getInstance();
		props.printAll();
		
		esClient = setupESConn();
		mongoDB  = setupMongoDBConn();
		catalogs = mongoDB.getCollection("catalogs");
	}

	private  Client setupESConn(){
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name",
				AppProperties.getInstance().getProperty("es.cluster.name")).build();

		return new TransportClient( settings ).addTransportAddress(
				new InetSocketTransportAddress(
						AppProperties.getInstance().getProperty("es.host.name"),
						Integer.parseInt( AppProperties.getInstance().getProperty("es.port")))) ;

	}
	
	public static MongoCollection<Document> catalogs() {
		return catalogs;
	}
	
	public static Client esClient() {
		return esClient;
	}
	
	private MongoDatabase setupMongoDBConn(){
		mongoClient = new MongoClient( "localhost" , 27017 );
		mongoDB = mongoClient.getDatabase("store");
		return mongoDB;
	}

	public void contextDestroyed( ServletContextEvent sce ){
		log.info("Closing ES Connection");
		esClient.close();
		
		log.info("Closing MongoDB Connection");
		mongoClient.close();
	}
}
