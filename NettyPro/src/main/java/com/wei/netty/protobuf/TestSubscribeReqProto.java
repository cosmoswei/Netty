package com.wei.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.util.ArrayList;
import java.util.List;

public class TestSubscribeReqProto {
    private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("cosmoswei");
        builder.setProductName("HUT");
        List<String> address = new ArrayList<>();
        address.add("HUNAN CHENZHOU");
        address.add("HUNAN ZHUZHOU");
        address.add("HUNAN CHANGSHA");
        builder.setAddress(address.toString());
        return builder.build();
    }

    public static void main(String[] args) throws InvalidProtocolBufferException {
        SubscribeReqProto.SubscribeReq req1 = createSubscribeReq();
        System.out.println("Before : " + req1);
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req1));
        System.out.println("Before : " + req2);
        System.out.println(req1.equals(req2));
    }
}
