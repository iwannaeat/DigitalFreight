package com.nccbc.digitalfreight.model;

import com.nccbc.digitalfreight.aelf.sdk.AElfClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: DigitalFreight
 * @description: JavaBean AElfchain
 * @author: Haochen Ren
 * @create: 2023-03-02 20:51
 **/
@Component
public class AElfChain {
    private AElfClient client;
    private String privateKey = "a7d5521609e2a1201c36bd4d88655dbc92136a2dcf9681a6d0403617ce7b2b48";
    private String contractaddress = "2RHf2fxsnEaM3wb6N1yGqPupNZbcCY98LgWbGSFWmWzgEs5Sjo";

    private AElfChain() {
        this.client = new AElfClient("http://121.36.90.112:8000");
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getContractaddress() {
        return contractaddress;
    }

    public void setContractaddress(String contractaddress) {
        this.contractaddress = contractaddress;
    }
}
