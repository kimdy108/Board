package com.project.board.main.api.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.spec.AlgorithmParameterSpec;
import java.util.UUID;

import static com.project.board.main.api.utils.seed.KISA_SEED_MAIN.*;

@Slf4j
public class Common {

    public class StatusCode {
        public static final int RETURN_SUCCESS = 1000;
        public static final int RETURN_AUTHFAIL = 9996;
        public static final int RETURN_EXPIRE = 9997;
        public static final int RETURN_NOTOKEN = 9998;
        public static final int RETURN_ERROR = 9999;
    }

    public class SeedCCMKey {
        private static final String keyString = "52A496XIBEOQ0N0LJB9DNU3QW7XGV2TC";
        private static final String nonceString = "7YKWYRKXUNQEUZFE47KXPDIN";
        private static final String addString = "7G9ZO9CTJFYJQW9MDB95FZH17UEUFTC95R15DGMON0DDUJ4JZI2LJMUZRDFI7YCC";
    }

    public static final String AUTHCHAR = "🄱🄾🄰🅁🄳"; // 🄰🄱🄲🄳🄴🄵🄶🄷🄸🄹🄺🄻🄼🄽🄾🄿🅀🅁🅂🅃🅄🅅🅆🅇🅈🅉
    public static final String DEFINEAUTHKEY = "zb7vhcQZ6QUTwKhKtj8SPmUrXkg1THNQ";
    public static final String DEFINEAUTHIV = "KIMDY@BOARDADMIN";

    public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final Long EMPTY_SEQ = 0L;

    public static String initEncryptStr(String plainStr) {
        byte[] initStrByte = plainStr.getBytes(StandardCharsets.UTF_8);
        return makeSeedCCMEncrypt(SeedCCMKey.keyString, SeedCCMKey.nonceString, SeedCCMKey.addString, bytesToHexString(initStrByte, initStrByte.length));
    }

    public static String initDecryptStr(String cipherStr) {
        try {
            return makeSeedCCMDecrypt(SeedCCMKey.keyString, SeedCCMKey.nonceString, SeedCCMKey.addString, cipherStr);
        } catch (Exception e) {
            return "";
        }
    }

    public static String encryptString(String data) {
        try {
            return encryptStringPrivate(data);
        }
        catch(Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    public static String encryptStringSalt(String data) {
        try {
            return encryptStringPrivate(RandomStringUtils.random(10, true, true) + AUTHCHAR + data + AUTHCHAR + RandomStringUtils.random(10, true, true));
        } catch (Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    private static String encryptStringPrivate(String ciphertext) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(DEFINEAUTHKEY.getBytes(StandardCharsets.UTF_8), "AES");

        String ivString = DEFINEAUTHIV.substring(0,16);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(ciphertext.getBytes(StandardCharsets.UTF_8));

        return new String(Base64.encodeBase64(encrypted));
    }

    public static String decryptString(String data) {
        try {
            return decryptStringPrivate(data);
        }
        catch(Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    public static String decryptStringSalt(String data) {
        try {
            return decryptStringPrivate(data).split(AUTHCHAR)[1];
        } catch (Exception e) {
            log.debug(e.getMessage());
            return "";
        }
    }

    private static String decryptStringPrivate(String encrypttext) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(DEFINEAUTHKEY.getBytes("UTF-8"), "AES");

        String ivString = DEFINEAUTHIV.substring(0,16);
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivString.getBytes(StandardCharsets.UTF_8));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decrypted = Base64.decodeBase64(encrypttext.getBytes(StandardCharsets.UTF_8));

        return new String(cipher.doFinal(decrypted), StandardCharsets.UTF_8);
    }
}
