package org.grant.domain.rpc.contants;

import java.util.Optional;

/**
 * grant
 * 20/9/2019 5:27 PM
 * 描述：
 */
public enum RpcType {
    MESSAGE,
    FILE,
    HEART_BEAT,
    RECEIPT;

    public static Optional<RpcType> valueOfByOptional(String val){
        RpcType rpcType = null;
        try {
            rpcType = RpcType.valueOf(val);
        }catch (IllegalArgumentException e){
            ;
        }
        return Optional.ofNullable(rpcType);
    }
}
