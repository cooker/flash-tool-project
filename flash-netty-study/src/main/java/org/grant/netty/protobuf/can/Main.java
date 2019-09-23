package org.grant.netty.protobuf.can;

import com.google.protobuf.InvalidProtocolBufferException;
import org.grant.netty.protobuf.TranMessage;

import java.nio.IntBuffer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * grant
 * 12/9/2019 11:50 AM
 * 描述：
 */
public class Main {
    public static void main(String[] args) throws InvalidProtocolBufferException {
//        TranMessage.Message tran = TranMessage.Message.newBuilder()
//                .setBody("sa")
//                .build();
//        System.out.println(tran.getBody());
//
//        byte[] bytes = tran.toByteArray();
//        tran =  TranMessage.Message.parseFrom(bytes);
//        System.out.println(tran.getBody());


        IntBuffer intBuffer = IntBuffer.allocate(10);
        intBuffer.put(1);
        intBuffer.flip();
        System.out.println(intBuffer.get());
        intBuffer.mark();
        intBuffer.reset();
        System.out.println(intBuffer.get());
        intBuffer.flip();
        intBuffer.put(2);
        intBuffer.flip();
        System.out.println(intBuffer.get());

    }
}