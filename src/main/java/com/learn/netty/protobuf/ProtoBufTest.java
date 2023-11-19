package com.learn.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class ProtoBufTest {
    public static void main(String[] args) throws Exception {
        DataInfo.Student student =
                DataInfo.Student.newBuilder().setName("����").setAge(20).setAddress("����").build();
        byte[] student2ByteArray = student.toByteArray();
        DataInfo.Student student2 = DataInfo.Student.parseFrom(student2ByteArray);
        System.out.println(student2.getName());
        System.out.println(student2.getAddress());
        System.out.println(student2.getAge());
    }
}
