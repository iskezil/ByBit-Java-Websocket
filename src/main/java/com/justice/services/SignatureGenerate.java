package com.justice.services;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.openmbean.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SignatureGenerate {
    public static String getSignature(String timestamp, String secretKey)
            throws NoSuchAlgorithmException, InvalidKeyException, java.security.InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(secretKeySpec);
        return bytesToHex(mac.doFinal(timestamp.getBytes()));
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte hashByte : bytes) {
            int intVal = 0xff & hashByte;
            if (intVal < 0x10) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(intVal));
        }
        return sb.toString();
    }
}
