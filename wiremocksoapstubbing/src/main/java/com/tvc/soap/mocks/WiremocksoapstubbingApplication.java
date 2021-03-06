package com.tvc.soap.mocks;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.standalone.CommandLineOptions;
import com.tvc.soap.mocks.stub.StubGenerator;

public class WiremocksoapstubbingApplication {
    static StubGenerator stub =new StubGenerator();
    public static void main (final String[] args){
        final String[] args2 = new String[args.length + 4];
        args2[args.length] = "--port";
        args2[args.length + 1] = "8001";
        args2[args.length + 2] = "--extensions";
        args2[args.length + 3] = "com.tvc.soap.mocks.WireMockExt";
        final CommandLineOptions options = new CommandLineOptions(args2);
        WireMockServer wireMockServer = new WireMockServer(options);
        wireMockServer.start();
        stub.loadStubs(wireMockServer);
    }
}




