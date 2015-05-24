package com.catalog.refresh.tasklet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.catalog.refresh.ESConnection;
import com.catalog.refresh.model.Catalog;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class PersistData implements Tasklet{
	@Override
	public RepeatStatus execute(StepContribution contribution, 
			ChunkContext chunkContext) throws Exception {

		System.out.println("Implementing Task2");
		FileInputStream fis = new FileInputStream("/Users/kborra/Documents/KSB/projects/My_Projects/Prosperant");
		readJsonStream(fis);
		return RepeatStatus.FINISHED;
	}

	public void readJsonStream(FileInputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			readMessage(reader);
		} finally {
			reader.close();
		}
	}

	public void readMessage(JsonReader reader) throws IOException {
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("data")) {
				readDataArray(reader);
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	public void readDataArray(JsonReader reader) throws IOException {
		reader.beginArray();
		while (reader.hasNext()) {
			readCatalog(reader);
		}
		reader.endArray();
	}

	public Catalog readCatalog(JsonReader reader) throws IOException {
		Catalog catalog = new Catalog();

		reader.beginObject();

		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("productId")) {
				catalog.setProductId(reader.nextString());
			} else if (name.equals("affiliate_url")) {
				catalog.setAffiliate_url(reader.nextString());
			} else if (name.equals("image_url")) {
				catalog.setImage_url(reader.nextString());
			} else if (name.equals("keyword")) {
				catalog.setKeyword(reader.nextString());
			} else if (name.equals("description")) {
				catalog.setDescription(reader.nextString());
			}else if (name.equals("currency")) {
				catalog.setCurrency(reader.nextString());
			}else if (name.equals("merchant")) {
				catalog.setMerchant(reader.nextString());
			}else if (name.equals("merchantId")) {
				catalog.setMerchantId(reader.nextString());
			}else if (name.equals("brand")) {
				catalog.setBrand(reader.nextString());
			}else if (name.equals("upc")) {
				catalog.setUpc(reader.nextString());
			}else if (name.equals("isbn")) {
				catalog.setIsbn(reader.nextString());
			} else if (name.equals("sales")) {
				catalog.setSales(reader.nextString());
			}else if (name.equals("catalogId")) {
				catalog.setCatalogId(reader.nextString());
			}else if (name.equals("category")) {
				catalog.setCategory(reader.nextString());
			}else {
				reader.skipValue();
			}
		}
		reader.endObject();
		System.out.println("catalog.. : "+catalog);

		persistCatalog(catalog);
		return catalog;
	}

	public void persistCatalog(Catalog catalog){
		try{
			Client client = ESConnection.getInstance().client();
			IndexResponse response = client.prepareIndex("collections", "catalog", catalog.getCatalogId())
					.setSource(new Gson().toJson(catalog))
					.execute()
					.actionGet();
		}catch(Exception e){
			System.out.println("Exception..");
			e.printStackTrace();
		}
	}
}
