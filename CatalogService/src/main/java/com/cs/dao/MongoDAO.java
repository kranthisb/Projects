package com.cs.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.cs.datasource.MongoDBDataSource;
import com.cs.servlet.listener.AppContext;

public class MongoDAO {
	
	public static Double getSearchCount(String productId){
		
		Document doc = MongoDBDataSource.getInstance()
				.mongoDB().getCollection("catalogs")
				.find(eq("productId", productId))
				.first();
		
		if(doc != null)
			return doc.getDouble("searchCount");
		return null;
	}
}
