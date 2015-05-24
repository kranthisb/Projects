package com.cs.dao;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;

import com.cs.servlet.listener.AppContext;

public class MongoDAO {
	
	public static Double getSearchCount(String productId){
		Document doc = AppContext.catalogs().find(eq("productId", productId)).first();
		if(doc != null)
			return doc.getDouble("searchCount");
		return null;
	}
}
