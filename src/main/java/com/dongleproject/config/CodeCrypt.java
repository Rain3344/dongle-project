package com.dongleproject.config;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

/**
 * @Title: CodeCrypt
 * @Author wyh@qq.com
 * @Package com.dongleproject.config
 * @Date 2025/12/2 14:31
 * @description:
 */
public class CodeCrypt {
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128; // GCM 鉴权标签长度
    private static final int IV_LENGTH_BYTE = 12;  // GCM 推荐 12 字节 IV
    private static final int SALT_LENGTH_BYTE = 16;
    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH_BIT = 256; // AES-256

    // 加密代码块 → Base64 密文
    public static String encrypt(String code, String password) throws Exception {
        // 1. 生成随机 Salt 和 IV
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        byte[] iv = new byte[IV_LENGTH_BYTE];
        random.nextBytes(salt);
        random.nextBytes(iv);

        // 2. 从密码派生密钥
        SecretKey key = deriveKey(password, salt);

        // 3. 加密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);
        byte[] timestampBytes = code.getBytes(StandardCharsets.UTF_8);
        byte[] ciphertext = cipher.doFinal(timestampBytes);

        // 4. 组合 Salt + IV + Ciphertext → Base64
        byte[] combined = new byte[salt.length + iv.length + ciphertext.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(iv, 0, combined, salt.length, iv.length);
        System.arraycopy(ciphertext, 0, combined, salt.length + iv.length, ciphertext.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    // 解密 Base64 密文 → 时间戳
    public static String decrypt(String encryptedBase64, String password) throws Exception {
        // 1. 解码 Base64
        byte[] combined = Base64.getDecoder().decode(encryptedBase64);

        // 2. 提取 Salt、IV、Ciphertext
        byte[] salt = new byte[SALT_LENGTH_BYTE];
        byte[] iv = new byte[IV_LENGTH_BYTE];
        byte[] ciphertext = new byte[combined.length - salt.length - iv.length];
        System.arraycopy(combined, 0, salt, 0, salt.length);
        System.arraycopy(combined, salt.length, iv, 0, iv.length);
        System.arraycopy(combined, salt.length + iv.length, ciphertext, 0, ciphertext.length);

        // 3. 派生密钥
        SecretKey key = deriveKey(password, salt);

        // 4. 解密
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, gcmSpec);
        byte[] plaintext = cipher.doFinal(ciphertext);
        return new String(plaintext, StandardCharsets.UTF_8);
    }

    // 从密码 + Salt 派生安全密钥
    private static SecretKey deriveKey(String password, byte[] salt) throws Exception {
        KeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                salt,
                ITERATIONS,
                KEY_LENGTH_BIT
        );
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyBytes, "AES");
    }

    public static void main(String[] args) throws Exception {
        String password = "1677FEDE1EDBBA5D53AC0CBAF1AC6E3C"; // 自定义密钥种子

        // 加密示例
        String code = "await engine.Engine3D.init({\n" +
                "            canvasConfig: descriptor.canvasConfig,\n" +
                "            beforeRender: descriptor.beforeRender,\n" +
                "            renderLoop: descriptor.renderLoop,\n" +
                "            lateRender: descriptor.lateRender,\n" +
                "            engineSetting: descriptor.engineSetting,\n" +
                "        });";
        System.out.println("原始代码: " + code);
        String encrypted = encrypt(code, password);
        System.out.println("加密结果: " + encrypted);

        // 解密示例
        String decryptedCode = decrypt(encrypted, password);
        System.out.println("解密结果: " + decryptedCode);
        System.out.println("是否匹配? " + (code.equals(decryptedCode)));
    }
}
