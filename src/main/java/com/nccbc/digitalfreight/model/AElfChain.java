package com.nccbc.digitalfreight.model;

import com.nccbc.digitalfreight.aelf.sdk.AElfClient;
import org.springframework.beans.factory.annotation.Value;

public class AElfChain {
    private AElfClient client;
    @Value("${aelf.client}")
    private String aelfClient;

    @Value("${aelf.privatekey}")
    private String privateKey;

    private AElfChain() {
        this.client = new AElfClient(aelfClient);
    }
    private static final AElfChain AELF_CHAIN = new AElfChain();

    public static AElfChain getInstance() {
        return AELF_CHAIN;
    }

    public AElfClient getClient() {
        return client;
    }

    public void setClient(AElfClient client) {
        this.client = client;
    }

    public String getAelfClient() {
        return aelfClient;
    }

    public void setAelfClient(String aelfClient) {
        this.aelfClient = aelfClient;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
