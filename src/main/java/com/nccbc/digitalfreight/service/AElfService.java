package com.nccbc.digitalfreight.service;

import com.google.protobuf.ByteString;
import com.nccbc.digitalfreight.aelf.protobuf.generated.Client;
import com.nccbc.digitalfreight.aelf.protobuf.generated.Core;
import com.nccbc.digitalfreight.aelf.protobuf.generated.DataStoreContractOuterClass;
import com.nccbc.digitalfreight.aelf.schemas.KeyPairInfo;
import com.nccbc.digitalfreight.aelf.schemas.SendTransactionInput;
import com.nccbc.digitalfreight.aelf.schemas.SendTransactionOutput;
import com.nccbc.digitalfreight.aelf.schemas.TransactionResultDto;
import com.nccbc.digitalfreight.aelf.utils.ByteArrayHelper;
import com.nccbc.digitalfreight.dao.AElfDao;
import com.nccbc.digitalfreight.model.AElfChain;
import org.bitcoinj.core.Base58;
import org.bitcoinj.wallet.Wallet;
import org.bouncycastle.util.encoders.Hex;

public class AElfService implements AElfDao {

    OrderService orderService = new OrderService();
    GasBillService gasBillService = new GasBillService();

    @Override
    public KeyPairInfo createWallet() throws Exception {
        try {
            return AElfChain.getInstance().getClient().generateKeyPairInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public Boolean AddUserContract(String privateKey) throws Exception {
//        return null;
//    }

    @Override
    public SendTransactionOutput SaveDataOnChain(String hash, String privateKey) throws Exception {
        // 通过节点 privateKey 获取节点地址，该地址即为合约的 owner
        String ownerAddress = AElfChain.getInstance().getClient().getAddressFromPrivateKey(privateKey);
        Client.Address.Builder owner = Client.Address.newBuilder();
        owner.setValue(ByteString.copyFrom(Base58.decodeChecked(ownerAddress)));

        // 构建合约调用时需要传递的参数
        // 具体定义见 io.aelf 包中的 proto 文件
        DataStoreContractOuterClass.HashStoreInput.Builder datastoreinput = DataStoreContractOuterClass.HashStoreInput.newBuilder();
        // 对不同字段设置相应值
        datastoreinput.setValue(hash);
        DataStoreContractOuterClass.HashStoreInput datastoreObj = datastoreinput.build();
        // 构建调用智能合约方法对应的参数
        Core.Transaction.Builder transactionDataStore = AElfChain.getInstance().getClient().generateTransaction(ownerAddress, AElfChain.getInstance().getContractaddress(), "VoteHashStore", datastoreObj.toByteArray());
        Core.Transaction transactionDataStoreObj = transactionDataStore.build();
        // 用 owner 地址对该交易签名
        String signature = AElfChain.getInstance().getClient().signTransaction(privateKey, transactionDataStoreObj);
        transactionDataStore.setSignature(ByteString.copyFrom(ByteArrayHelper.hexToByteArray(signature)));
        transactionDataStoreObj = transactionDataStore.build();

        // 发送交易，该逻辑主要对应合约中的set方法
        SendTransactionInput sendTransactionInputObj = new SendTransactionInput();
        sendTransactionInputObj.setRawTransaction(Hex.toHexString(transactionDataStoreObj.toByteArray()));
        SendTransactionOutput sendResult = AElfChain.getInstance().getClient().sendTransaction(sendTransactionInputObj);
        return sendResult;

//        TransactionResultDto transactionResult;
//        // 循环查询接口，根据id获得交易执行结果
//        while (true) {
//            transactionResult = AElfChain.getInstance().getClient().getTransactionResult(sendResult.getTransactionId());
//            if ("MINED".equals(transactionResult.getStatus())) {
//                if (type == 0){
//
//                }
//                Voteblockchain voteBlockChain = new Voteblockchain();
//                voteBlockChain.setTransactionId(transactionResult.getTransactionId());
//                voteBlockChain.setBlockHeight(transactionResult.getBlockNumber());
//                voteBlockChain.setBlockHash(transactionResult.getBlockHash());
//                voteBlockChain.setChainStatus(transactionResult.getStatus());
//                voteBlockChainMapper.insert(voteBlockChain);
//                // 当状态为MINED表示执行成功，直接返回
//                return true;
//            } else if ("PENDING".equals(transactionResult.getStatus())) {
//                // 当状态为PENDING表示还未获取到结果，等待
//                Thread.sleep(300);
//            }
//        }
    }
}
