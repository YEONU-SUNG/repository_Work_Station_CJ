package com.neo.visitor.config;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by KaSha on 30/10/2018.
 */
public class AES256Util {
    private static final String PRIVATE_KEY = "SSYKeyForSportsCenter";
    private static String keyLimit() {
        return PRIVATE_KEY.substring(0, 16);
    }

    /**
     * 16자리의 키값을 입력
     * @throws UnsupportedEncodingException 키값의 길이가 16미만일 경우 발생
     */
    private static Key getKey() throws UnsupportedEncodingException {
        byte[] keyBytes = new byte[16];
        byte[] b = PRIVATE_KEY.getBytes("UTF-8");
        int len = b.length;
        if(len > keyBytes.length){
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);

        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException{
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, getKey(), new IvParameterSpec(keyLimit().getBytes()));
        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
        return new String(Base64.encodeBase64(encrypted));
    }

    /**
     * AES256 으로 암호화 한다.
     * @param str 암호화할 문자열
     * @return
     * @throws NoSuchAlgorithmException
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    public static String decrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {
    	if(str.contains("&quot")) {
    		str = str.replaceAll("&quot", "/");
		}
        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, getKey(), new IvParameterSpec(keyLimit().getBytes()));
        byte[] byteStr = Base64.decodeBase64(str.getBytes());
        return new String(c.doFinal(byteStr), "UTF-8");
    }
}