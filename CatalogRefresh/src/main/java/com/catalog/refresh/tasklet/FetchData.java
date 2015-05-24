package com.catalog.refresh.tasklet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.http.client.ClientProtocolException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class FetchData implements Tasklet{
	@Override
	public RepeatStatus execute(StepContribution contribution, 
			ChunkContext chunkContext) throws Exception {

		System.out.println("Fetching Data from Prosperent");

		BufferedReader br = null;
		OutputStream os = null;
		
		try {
			ClientRequest request = new ClientRequest("http://api.prosperent.com/api/search?query=men&imageSize=250x250&api_key=e93b0bcbea965f194d78969bd2930b06&limit=50");
			request.accept("application/json");
			ClientResponse<String> response = request.get(String.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "+ response.getStatus());
			}

			br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));

			os= new BufferedOutputStream(
                    new FileOutputStream("/Users/kborra/Documents/KSB/projects/My_Projects/Prosperant"));

			String output;
			
			while ((output = br.readLine()) != null) {
				os.write(output.getBytes());
			}			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			os.close();
			br.close();
		}

		return RepeatStatus.FINISHED;
	}
}
