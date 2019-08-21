package com.tvc.soap.mocks.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

import com.github.tomakehurst.wiremock.WireMockServer;

public class StubGenerator {
	
	 private WireMockServer wiremockserver;

	    public void loadStubs(WireMockServer wireMockServer){

	       wiremockserver = wireMockServer;

	        wiremockserver.stubFor(get(urlEqualTo("/api/get-magic"))
	                .willReturn(aResponse()
	                        .withHeader("Content-Type", "application/json")
	                        .withBody("{\"currently\":{\"windSpeed\":12.34}}")));

	        wiremockserver.stubFor(get(urlPathMatching("/response-transform-with-params"))
	                .withQueryParam("emp_no",matching( "..*" ))
	                .willReturn(aResponse()
	                        .withHeader("Content-Type", "application/xml")
	                        .withTransformers("wiremock-ext")));

	        wiremockserver.stubFor(get(urlEqualTo("/my/resource"))
	                .withHeader("Accept", containing("application/json"))
	                .willReturn(aResponse()
	                        .withStatus(200)
	                        .withHeader("Content-Type", "application/json")
	                        .withBody("{\"response\":Some content}")));
	   }


}
