package org.kwet.giteway.data.impl;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.test.web.client.RequestMatchers.method;
import static org.springframework.test.web.client.RequestMatchers.requestTo;
import static org.springframework.test.web.client.ResponseCreators.withSuccess;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;


public abstract class BaseGitConnectorTest{

	protected RestTemplate restTemplate;
	
	protected void configureRestTemplateMock(String url, String responseFile){
		
		MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo(url))
		.andExpect(method(GET))
		.andRespond(withSuccess(jsonResource(responseFile), MediaType.APPLICATION_JSON));
	}
	

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}
	
	
	
}
