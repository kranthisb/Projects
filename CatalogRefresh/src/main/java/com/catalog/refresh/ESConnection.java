package com.catalog.refresh;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESConnection {
	private Client client;
	
	private static ESConnection instance = null;
	
	private ESConnection(){
		client = connect();
		System.out.println("Created new ES Connection: "+client);
	}
	
	public static ESConnection getInstance(){
		if(instance == null){
			instance = new ESConnection();
		}
		return instance;
	}
	
	private Client connect(){
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "cornerstone").build();
		
		return new TransportClient(settings).addTransportAddress(new InetSocketTransportAddress("ec2-52-26-87-176.us-west-2.compute.amazonaws.com",9300));
	}
	
	public Client client() {
		return client;
	}
}
