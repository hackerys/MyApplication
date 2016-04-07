package com.jansen.myapplication.utils;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 使用DES加密与解密
 */
public class AESEncryptSafe {

    public static final String VIPARA = "8*kd)2A5$k/s9Fd5";// "8*kd)2A5$k/s9Fd5";
    public static final String charset = "UTF-8";

    private final IvParameterSpec zeroIv = new IvParameterSpec(
            VIPARA.getBytes());
    private final SecretKeySpec key;
    private final String type = "AES";

    // 根据参数生成KEY
    public AESEncryptSafe(String strKey) {
        try {
            key = new SecretKeySpec(MD5Util.MD5_16(strKey).toLowerCase()
                    .getBytes(charset), type);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String cleartext) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encrypted = cipher.doFinal(fullZero(cleartext, blockSize));
            return Base64.encode(encrypted).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String encrypt2(byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
            byte[] encrypted = cipher.doFinal(fullZero(data, blockSize));
            return Base64.encode(encrypted).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String decrypt(String encrypted) {
        try {
            byte[] byteMi = Base64.decode(encrypted);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, charset).trim();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] decrypt2(String encrypted) {
        try {
            byte[] byteMi = Base64.decode(encrypted);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
            byte[] decryptedData = cipher.doFinal(byteMi);
            return decryptedData;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] fullZero(byte[] dataBytes, int blockSize)
            throws UnsupportedEncodingException {
//		byte[] dataBytes = data.getBytes(charset);
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength
                    + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        return plaintext;
    }

    public static byte[] fullZero(String data, int blockSize)
            throws UnsupportedEncodingException {
        byte[] dataBytes = data.getBytes(charset);
        int plaintextLength = dataBytes.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength
                    + (blockSize - (plaintextLength % blockSize));
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
        return plaintext;
    }
}
