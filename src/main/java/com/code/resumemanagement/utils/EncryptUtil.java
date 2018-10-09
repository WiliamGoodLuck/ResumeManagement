package com.code.resumemanagement.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
public class EncryptUtil {

    public static String md5(String password){
        return DigestUtils.md5Hex(password);
    }

    public static String md5hash(String password, String salt){
        return new Md5Hash(password, salt, 2).toString();
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.md5hash("1", "aaa@code.com"));
    }
}
