package com.cs.datasource;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.cs.util.Props;

public class ESDataSource extends DataSource{
	final static Logger log = Logger.getLogger(ESDataSource.class);

	private static ESDataSource esDataSource;
	private Client client;

	private ESDataSource(){
	}

	public String name(){
		return "ES";
	}
	
	public static ESDataSource getInstance(){
		if(esDataSource == null){
			esDataSource = new ESDataSource();
		}
		return esDataSource;
	}
	
	public Client client() {
		return client;
	}
	
	public void connect(){
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name",
				Props.getInstance().get("es.cluster.name")).build();

		client =  new TransportClient( settings ).addTransportAddress(
				new InetSocketTransportAddress(
						Props.getInstance().get("es.host.name"),
						Integer.parseInt( Props.getInstance().get("es.port")))) ;	
		
		log.info("Connected to ES :"+client);
	}

	public void close(){
		if(client != null)  {
			client.close();
			log.info("Successfully closed ES conection");
		}
	}

	public void monitorDataSourceHealth(){ 
		byte status = 0;
		try {
			status = client.admin().cluster().health(new ClusterHealthRequest()).get().getStatus().value();
		} catch (Exception e) {
			log.info("Error while retrieving cluster status : "+e.getMessage());
			e.printStackTrace();
		}

		log.error("ES Cluster status :"+ClusterHealthStatus.fromValue(status));

		//TODO :  Add more conditions to determine if Cluster is in good health...

		if(status != 0){ //Not Green
			log.error("ES Cluster is not healthy. Please check.");
		}
	}

	
}
