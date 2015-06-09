package com.cs.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import com.cs.dao.MongoDAO;
import com.cs.dao.SearchDAO;

public class CSService {
	final static Logger log = Logger.getLogger(CSService.class);

	public static String getCatalogCatagories() throws JsonGenerationException, JsonMappingException, IOException{
		String category = null;
		Map<Object, Object> results = new HashMap<Object, Object>();
		Map<String, Object> categories = null;

		SearchResponse response = SearchDAO.getCatalogCategories();

		try {
			for (SearchHit hit : response.getHits()) {
				if (hit == null) {
					//TODO handle
				}
				categories = hit.getSource();
				category = categories.get("category").toString();
				results.put(hit, category);
			}
		} catch (Exception e) {
			//TODO :  Handle
		}
		return new ObjectMapper().writeValueAsString(results.values());
	}

	public static String getBrands(String category) throws JsonGenerationException, JsonMappingException, IOException{
		String brand = null;
		Set<String> results = new HashSet();
		SearchResponse response = SearchDAO.getBrands(category);
		Map<String, Object> brands = null;

		try {
			for (SearchHit hit : response.getHits()) {
				if (hit == null) {
					//TODO handle
				}
				brands = hit.getSource();
				brand = brands.get("brand").toString();
				results.add(brand);
			}
		} catch (Exception e) {
			//TODO :  Handle
		}
		return new ObjectMapper().writeValueAsString(results);
	}
	
	public static String getAllProducts() throws JsonGenerationException, JsonMappingException, IOException{
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		SearchResponse response = SearchDAO.getAllProducts();
		Map<String, Object> products = null;

		try {
			for (SearchHit hit : response.getHits()) {
				if (hit == null) {
					//TODO handle
				}
				products = hit.getSource();

				results.add(products);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ObjectMapper().writeValueAsString(results);
	}
	
	public static String getProducts(String category, String brand) throws JsonGenerationException, JsonMappingException, IOException{
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		SearchResponse response = SearchDAO.getProducts(category, brand);
		Map<String, Object> products = null;

		try {
			for (SearchHit hit : response.getHits()) {
				if (hit == null) {
					//TODO handle
				}
				products = hit.getSource();

				//Kranthi : uncoment it after adding MongoDB instance...
//				Double searchCount = MongoDAO.getSearchCount(products.get("productId").toString());
//				if(searchCount!= null) {
//					products.put("searchCount", searchCount);
//				}
				results.add(products);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return new ObjectMapper().writeValueAsString(results);
	}
}
