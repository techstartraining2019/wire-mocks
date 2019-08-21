package wiremock.transformation.learning;

import com.github.tomakehurst.wiremock.WireMockServer;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class stubbing {

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
