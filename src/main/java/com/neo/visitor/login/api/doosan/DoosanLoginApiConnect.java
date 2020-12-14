package com.neo.visitor.login.api.doosan;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.net.HttpURLConnection;

import java.io.*;
import java.net.MalformedURLException;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.crypto.Cipher;

public class DoosanLoginApiConnect {

    private static final String AD_URL = "https://booking.doosan.com/api/auth/login";

    private static class ASE {
        final static int PRIVATE_KEY_SIZE = 16;
        final static String PRIVATE_KEY = "passwordQ1W2E3R!";

        static Key getKey() throws UnsupportedEncodingException {

            byte[] keyBytes = new byte[PRIVATE_KEY_SIZE];
            byte[] b = PRIVATE_KEY.getBytes("UTF-8");
            int len = b.length;
            if (len > keyBytes.length) {
                len = keyBytes.length;
            }
            System.arraycopy(b, 0, keyBytes, 0, len);
            return new SecretKeySpec(keyBytes, "AES");
        }

        public static String encrypt(String str) throws GeneralSecurityException, UnsupportedEncodingException {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, getKey(), new IvParameterSpec(PRIVATE_KEY.getBytes())); 
            byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
            //return new String(Base64.encodeBase64(encrypted));
            return str;
        }
    }

    public static DoosanADResponse postRequest(String email, String password) throws IOException {

        try {

            TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] x509Certificates, String s) throws CertificateException {
                }
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }
            };

            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            
            // URL 설정하고 접속하기
            URL url = new URL(AD_URL); // URL 설정
            HttpsURLConnection https = (HttpsURLConnection) url.openConnection(); // 접속
            // --------------------------
            // 전송 모드 설정 - 기본적인 설정
            // --------------------------
            https.setDefaultUseCaches(false);
            https.setDoInput(true); // 서버에서 읽기 모드 지정
            https.setDoOutput(true); // 서버로 쓰기 모드 지정
            https.setRequestMethod("POST"); // 전송 방식은 POST
            https.setConnectTimeout(20000);
            https.setReadTimeout(6000);
            // --------------------------
            // 헤더 세팅
            // --------------------------
            https.setRequestProperty("content-type", "application/json");

            Map<String, String> map = new HashMap<>();

            try {
                map.put("email", ASE.encrypt(email));
                map.put("password", ASE.encrypt(password));
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            OutputStreamWriter outStream = new OutputStreamWriter(https.getOutputStream(), "UTF-8");
            PrintWriter writer = new PrintWriter(outStream);
            writer.write(new Gson().toJson(map));
            writer.flush();

            //--------------------------
            //   서버에서 전송받기
            //--------------------------
            InputStreamReader tmp = new InputStreamReader(https.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(tmp);
            StringBuilder builder = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                builder.append(str + "\n");
            }

            return new Gson().fromJson(builder.toString(), DoosanADResponse.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new IOException("api 인증 에러");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }

    // public static DoosanADResponse postRequest(String email, String password) throws IOException {

    //     try {
    //         // URL 설정하고 접속하기
    //         URL url = new URL(AD_URL); // URL 설정
    //         HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속
    //         // --------------------------
    //         // 전송 모드 설정 - 기본적인 설정
    //         // --------------------------
    //         http.setDefaultUseCaches(false);
    //         http.setDoInput(true); // 서버에서 읽기 모드 지정
    //         http.setDoOutput(true); // 서버로 쓰기 모드 지정
    //         http.setRequestMethod("POST"); // 전송 방식은 POST
    //         // --------------------------
    //         // 헤더 세팅
    //         // --------------------------
    //         http.setRequestProperty("content-type", "application/json");

    //         Map<String, String> map = new HashMap<>();

    //         try {
    //             map.put("email", ASE.encrypt(email));
    //             map.put("password", ASE.encrypt(password));
    //         } catch (GeneralSecurityException e) {
    //             e.printStackTrace();
    //         }

    //         OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
    //         PrintWriter writer = new PrintWriter(outStream);
    //         writer.write(new Gson().toJson(map));
    //         writer.flush();

    //         //--------------------------
    //         //   서버에서 전송받기
    //         //--------------------------
    //         InputStreamReader tmp = new InputStreamReader(http.getInputStream(), "UTF-8");
    //         BufferedReader reader = new BufferedReader(tmp);
    //         StringBuilder builder = new StringBuilder();
    //         String str;
    //         while ((str = reader.readLine()) != null) {
    //             builder.append(str + "\n");
    //         }

    //         return new Gson().fromJson(builder.toString(), DoosanADResponse.class);

    //     } catch (MalformedURLException e) {
    //         e.printStackTrace();
    //     } catch (IOException e) {
    //         throw new IOException("api 인증 에러");
    //     }
    //     return null;
    // }

}
