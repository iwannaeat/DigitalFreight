package com.nccbc.digitalfreight.dao;

import com.nccbc.digitalfreight.aelf.schemas.KeyPairInfo;
import com.nccbc.digitalfreight.aelf.schemas.SendTransactionOutput;

public interface AElfDao {
    KeyPairInfo createWallet() throws Exception;

    //Boolean AddUserContract(String privateKey) throws Exception;

    SendTransactionOutput SaveDataOnChain(String hash, String privateKey)throws Exception;
}
