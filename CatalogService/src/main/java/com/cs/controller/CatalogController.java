package com.cs.controller;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import com.cs.service.CSService;

@Path("/")
public class CatalogController {
	final static Logger logger = Logger.getLogger(CatalogController.class);
	
	/*
	 * Get all available categories
	 * curl -v http://localhost:8080/CatalogService/catalog
	 * Equivalent to 
	 *	{
	 *	"fields" : ["category"]
	 *	}
	 */
	
	@GET
	@Path("catalog")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCategories() {
		try {
			return CSService.getCatalogCatagories();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	/*
	 * Get all brands for a given category
	 * curl -v http://localhost:8080/CatalogService/catalog/Clothing
	 * Equivalent to 
	 * {
	 *	"fields" : ["brand"],
	 *	"query": {
     *	"bool": {
     *	"must": [
     *       {"match": { "category": "Clothing" }}
     *	]
	 *	}}}
	 */
	
	@GET
	@Path("catalog/{identifier}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getBrand(@PathParam("identifier") String category) {
		try {
			return CSService.getBrands(category);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/*
	 * Get all products for a given category and brand
	 * curl -v http://localhost:8080/CatalogService/catalog/Clothing/RedHead
	 * Equivalent to 
	 * {
	 * "fields" : ["keyword", "price", "image_url"],
	 * "query": {
	 * 		"bool": {
	 * 			"must": [
	 * 			{"match": { "category": "Men's Clothing" }},
	 * 			{"match": { "brand": "RedHead" }}
	 * 		]
	 * }}}
	 */
	
	@GET
	@Path("catalog/{category}/{brand}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getProduct(@PathParam("category") String category,@PathParam("brand") String brand) {
		try {
			return CSService.getProducts(category, brand);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
		
	@GET
	@Path("catalog/health")
	public Response healthcheck() { 
		String result = "Successful";
		return Response.status(200).entity(result).build();
	}
}
