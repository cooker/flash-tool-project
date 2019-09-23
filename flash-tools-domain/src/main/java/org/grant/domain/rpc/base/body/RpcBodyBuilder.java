package org.grant.domain.rpc.base.body;

import com.sun.tools.javac.util.Assert;
import org.grant.domain.rpc.RpcBody;
import org.grant.domain.rpc.RpcMessage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * grant
 * 23/9/2019 10:42 AM
 * 描述：
 */
public class RpcBodyBuilder {

    public static RpcBody<HeartBeat> buildHeartBeat(){
        HeartBeat heartBeat = new HeartBeat();
        RpcBody<HeartBeat> rpcBody = new RpcBody<>(heartBeat);
        return rpcBody;
    }

    public static RpcBody<FileWapper> buildFileWapper(File file) throws IOException {
        Assert.checkNonNull(file);
        FileWapper fileWapper = new FileWapper(file);
        RpcBody<FileWapper> rpcBody = new RpcBody<>(fileWapper);
        return rpcBody;
    }

    public static RpcBody<FileWapper> buildFileWapper(URL url) throws IOException {
        Assert.checkNonNull(url);
        FileWapper fileWapper = new FileWapper(url);
        RpcBody<FileWapper> rpcBody = new RpcBody<>(fileWapper);
        return rpcBody;
    }

    public static RpcBody<Receipt> buildReceipt(RpcMessage rpcMessage){
        Receipt receipt = new Receipt(rpcMessage);
        RpcBody<Receipt> rpcBody = new RpcBody<>(receipt);
        return rpcBody;
    }

}
