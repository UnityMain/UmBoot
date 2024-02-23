package com.unitymain.core.utils;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;

public class Demo10 {
    public static void main(String[] args) {
        byte[] decode = Base64.decode("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");
        //byte 转文件
        FileUtil.writeBytes(decode,"/Users/panyuanbiao/Downloads/demo.rar");
    }
}