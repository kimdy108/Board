package com.project.board.main.api.utils.seed;

public class KISA_SEED_MAIN {
    public static String makeSeedCCMEncrypt(String keyString, String nonceString, String addString ,String inString) {
        byte[] key = new byte[160];
        byte[] in = new byte[160];
        byte[] out = new byte[160];
        byte[] nonce = new byte[160];
        byte[] aad = new byte[160];

        KISA_SEED_CCM seed_ccm = new KISA_SEED_CCM();

        int keyLen = 0, inLen = 0, outLen = 0, nonceLen = 0, aadLen = 0, macLen = 16;

        keyLen = asc2hex(key, keyString);
        nonceLen = asc2hex(nonce, nonceString);
        aadLen = asc2hex(aad, addString);
        inLen = asc2hex(in, inString);

        outLen = seed_ccm.SEED_CCM_Encryption(out, in, inLen, macLen, nonce, nonceLen, aad, aadLen, key);

        return bytesToHexString(out, outLen);
    }

    public static String makeSeedCCMDecrypt(String keyString, String nonceString, String addString ,String encString) {
        String decResult = "";
        byte[] key = new byte[160];
        byte[] en = new byte[160];
        byte[] out = new byte[160];
        byte[] nonce = new byte[160];
        byte[] aad = new byte[160];

        KISA_SEED_CCM seed_ccm = new KISA_SEED_CCM();

        int keyLen = 0, enLen = 0, outLen = 0, nonceLen = 0, aadLen = 0, macLen = 16;

        keyLen = asc2hex(key, keyString);
        nonceLen = asc2hex(nonce, nonceString);
        aadLen = asc2hex(aad, addString);
        enLen = asc2hex(en, encString);

        outLen = seed_ccm.SEED_CCM_Decryption(out, en, enLen, macLen, nonce, nonceLen, aad, aadLen, key);

        decResult = bytesToHexString(out, outLen);
        byte[] decByteArray = hexStringToByteArray(decResult.toUpperCase());

        return new String(decByteArray);
    }

    private static int asc2hex(byte[] dst, String src) {
        byte temp = 0x00, hex = 0;
        int i = 0;
    
        for (i = 0; i < src.length(); i++)
        {
            temp = 0x00;
            hex = (byte)src.charAt(i);
    
            if ((hex >= 0x30) && (hex <= 0x39))
                temp = (byte)(hex - 0x30);
            else if ((hex >= 0x41) && (hex <= 0x5A))
                temp = (byte)(hex - 0x41 + 10);
            else if ((hex >= 0x61) && (hex <= 0x7A))
                temp = (byte)(hex - 0x61 + 10);
            else
                temp = 0x00;
            
            if ((i & 1) == 1)
                dst[i >> 1] ^= temp & 0x0F;
            else
                dst[i >> 1] = (byte)(temp << 4);
        }
    
        return ((i + 1) / 2);
    }

    public static String bytesToHexString(byte[] data, int len) {
        StringBuilder sb = new StringBuilder();
        for (int i  = 0; i < len; i++) {
            sb.append(String.format("%02x", data[i] & 0xff));
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String str) {
        int len = str.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(str.charAt(i), 16) << 4)
                    + Character.digit(str.charAt(i+1), 16));
        }
        return data;
    }
}