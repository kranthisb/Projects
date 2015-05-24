var express = require('express');
var router = express.Router();
var elasticsearch = require('elasticsearch');
var MongoClient = require('mongodb').MongoClient;

var index = "collections";
var type = "catalog";
var counter = 0;

//Elastic Search Connection
var client = elasticsearch.Client({
  host: 'localhost:9200',
  sniffOnStart: true,
  sniffInterval: 300000
});



// MongoDB Connection
MongoClient.connect("mongodb://localhost/store", function(err, database) {
  if(err) {
	console.log("Error connecting to DB");
  	throw err;
  }
  console.log("Connected to DB");
  db = database;
});


//http://localhost:3000/category
router.get("/category", function (req, res) {
   counter = counter+1;
   client.search({
	  index: index,
	  size: 50,
	  body: {
		"fields" : ["category"]
	  }
	}).then(function (resp) {
	  var hits = resp.hits.hits;
	  
	  var categories = new Array();
	  
	  for (var i in hits) {
        var fields = hits[i].fields
        if(fields){
        	var category = fields.category;
        	 categories.push(category[0]);
        	 
        }
      }    
//       console.log(categories);
	  res.json(categories);
	  console.log(counter);
	});
});


//curl -v http://localhost:3000/category/Clothing/
router.get("/category/:category", function (req, res) {
	var category = req.params.category;
	client.search({
	  index: index,
	  size: 50,
	  body: {
		
		"fields" : ["brand"],
		"query": {
			"bool": {
				"must": [
					{"match": { "category": category }}
				]
		}}}
	  
	}).then(function (resp) {
		var hits = resp.hits.hits;
		var brands = new Array();

		for (var i in hits) {
			var fields = hits[i].fields
			if(fields){
				var brand = fields.brand;
				brands.push(brand[0]);
				console.log(brand[0]);
			}
		}    
		res.json(brands)
	});
});

//curl -v http://localhost:3000/category/Clothing/RedHead
router.get("/category/:category/:brand", function (req, res) {
	var category = req.params.category;
	var brand = req.params.brand;
		
	client.search({
	  index: index,
	  size: 50,
	  body: {
		
		"fields" : ["productId", "keyword", "price", "image_url"],
		"query": {
			"bool": {
				"must": [
					{"match": { "category": category}},
					{"match": { "brand": brand }}
			]
		}}}
	  
	}).then(function (resp) {
		var hits = resp.hits.hits;
		console.log(hits);
		var products = new Array();

		 
		for (var i in hits){
			var fields = hits[i].fields
			if(fields){
				var productId = fields.productId[0];
				var product = {
					image_url:fields.image_url[0],
					price:fields.price[0],
					keyword:fields.keyword[0],
					productId:productId,
					searchcount:0
				};
				if(productId){
					console.log("productId ..."+productId);
					db.collection("catalogs").find({productId:productId}, function (err, results){
						if (err) {
							console.log("Error fetching catalogs");
							throw err;
						}
						console.log("Results.."+results);
						console.log("Match Found :"+product.productId, results.searchCount);
						product.searchcount=results.searchCount
						console.log("COunt:"+product.searchcount);

						console.log("Product..."+product);
						products.push(product);
					});
				}
			}
	 	}
		
		console.log("Returning response");
		res.json(products);
		  
		
	});
});
	
/* GET Hello World page. */
router.get('/health', function(req, res) {
    res.json("I am healthy")
});

module.exports = router;