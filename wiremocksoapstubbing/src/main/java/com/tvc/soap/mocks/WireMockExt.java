package com.tvc.soap.mocks;

import java.util.Map;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.ListOrSingle;
import com.github.tomakehurst.wiremock.extension.Parameters;
import com.github.tomakehurst.wiremock.extension.ResponseTransformer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.RequestTemplateModel;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.Response;
import com.tvc.soap.mocks.db.DBProcessor;



public class WireMockExt extends ResponseTransformer{
	
	 private Map<String,ListOrSingle<String>> requestParams;
	    DBProcessor db = new DBProcessor();
	    private String responsebody = null;
	    public Response transform(Request request, Response response, FileSource fileSource, Parameters parameters) {
	        requestParams = RequestTemplateModel.from(request).getQuery();

	        try {
	            responsebody = db.executequery(requestParams);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        final Response.Builder builder = Response.Builder.like(response).but();
	        builder.body(responsebody);
	        return builder.build();
	    }

	    public String getName() {
	        return "wiremock-ext";
	    }

	    public boolean applyGlobally() {
	        return false;
	    }


}
