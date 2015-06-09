package com.cs.dao;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.cs.datasource.ESDataSource;
import com.cs.servlet.listener.AppContext;
import com.cs.util.Props;

public class SearchDAO {
	final static Logger log = Logger.getLogger(SearchDAO.class);

	public static SearchResponse getCatalogCategories(){
		SearchResponse response = null;
		try {
			QueryBuilder qb = QueryBuilders.matchAllQuery();
			String[] includes = { "category" };
			response = searchCatalog().setQuery(qb).setFetchSource(includes, null).execute().actionGet();
			
			if (response != null && response.getFailedShards() > 0) {
				log.error("Shard Failures Exception");
				//TODO Handle Exceptions
				}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			//TODO Handle Exceptions
		}
		if (response == null) {
			//TODO Handle Exceptions
		}
		return response;
	}
	
	public static SearchResponse getBrands(String category){
		SearchResponse response = null;
		try {
			QueryBuilder qb = QueryBuilders.multiMatchQuery(category, "category");
			
			String[] includes = { "brand" };
			response = searchCatalog().setQuery(qb).setFetchSource(includes, null).execute().actionGet();
			if (response != null && response.getFailedShards() > 0) {
				log.error("Shard Failures Exception");
				//TODO Handle Exceptions
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			//TODO Handle Exceptions
		}
		if (response == null) {
			//TODO Handle Exceptions
		}
		log.info("getBrands method executed successfully");

		return response;
	}
	
	public static SearchResponse getAllProducts(){
		SearchResponse response = null;
		try {
			QueryBuilder qb = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery());
			
			String[] includes = { "productId", "keyword", "price", "image_url" };			
			response = searchCatalog().setQuery(qb).setFetchSource(includes, null).execute().actionGet();
						
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		if (response == null) {
			//TODO Handle Exceptions
		}
		return response;
	}
	
	public static SearchResponse getProducts(String category, String brand){
		SearchResponse response = null;
		try {
			QueryBuilder qb = QueryBuilders.boolQuery()
			        .must(QueryBuilders.matchQuery("category", category))
			        .must(QueryBuilders.matchQuery("brand", brand));
			
			String[] includes = { "productId", "keyword", "price", "image_url" };			
			response = searchCatalog().setQuery(qb).setFetchSource(includes, null).execute().actionGet();
						
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		if (response == null) {
			//TODO Handle Exceptions
		}
		return response;
	}
		
	private static SearchRequestBuilder searchCatalog() {
		return ESDataSource.getInstance().client()
				.prepareSearch(Props.getInstance().getIndexName())
				.setTypes(Props.getInstance().getType())
				.setSearchType(SearchType.DFS_QUERY_AND_FETCH);
	}

}
