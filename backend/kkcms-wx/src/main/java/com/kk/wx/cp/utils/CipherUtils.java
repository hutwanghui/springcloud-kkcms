package com.kk.wx.cp.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 提供加密解密相关实用方法
 * User: Xu Jin
 * Date: 2012-09-18
 * Time: 12:20
 */
public class CipherUtils {
    public static final Logger LOGGER = LoggerFactory.getLogger(CipherUtils.class);

    /**
     * RSA 密钥对
     */
    private static final KeyPair KEY_PAIR;

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);

        KEY_PAIR = CipherUtils.generateRsaKeyPair();
    }

    /**
     * 加密算法：AES
     */
    public static final String ALGORITHM_AES = "AES";

    /**
     * 加密算法：RSA
     */
    public static final String ALGORITHM_RSA = "RSA";

    /**
     * 获取 RSA 密钥对
     * @return
     */
    public static KeyPair getRsaKeyPair() {
        return KEY_PAIR;
    }

    /**
     * 产生 AES 密钥 <br/>
     * Key Size：512
     * @return
     */
    public static SecretKey generateAesSecretKey() {
        return generateAesSecretKey(512);
    }

    /**
     * 产生 AES 密钥 <br/>
     * @param keySize
     * @return
     */
    public static SecretKey generateAesSecretKey(int keySize) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM_AES, BouncyCastleProvider.PROVIDER_NAME);
            keyGenerator.init(keySize);
            return keyGenerator.generateKey();
        }
        catch(Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 保存密钥
     * @param secretKey 密钥
     * @param file 待保存的密钥文件
     */
    public static void saveSecretKey(SecretKey secretKey, File file) {
        try {
            FileUtils.writeByteArrayToFile(file, secretKey.getEncoded());
        }
        catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 加载密钥
     * @param file 密钥文件
     * @param algorithm 密钥算法
     * @return
     * @throws IOException
     */
    public static SecretKey loadSecretKey(File file, String algorithm) {
        try {
            return new SecretKeySpec(FileUtils.readFileToByteArray(file), algorithm);
        }
        catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 从资源加载密钥
     * @param resName 资源名称
     * @param algorithm 密钥算法
     * @return
     * @throws IOException
     */
    public static SecretKey loadSecretKey(String resName, String algorithm) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copy(CipherUtils.class.getResourceAsStream(resName), byteArrayOutputStream);
            return new SecretKeySpec(byteArrayOutputStream.toByteArray(), algorithm);
        }
        catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 产生 RAS 密钥对（公钥、私钥）<br/>
     * Key Size：512
     * @return
     */
    public static KeyPair generateRsaKeyPair() {
        return generateRsaKeyPair(512);
    }

    /**
     * 产生 RAS 密钥对（公钥、私钥）
     * @param keySize
     * @return
     */
    public static KeyPair generateRsaKeyPair(int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA, BouncyCastleProvider.PROVIDER_NAME);
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGenerator.initialize(keySize, new SecureRandom());
            return keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            LOGGER.error("产生 RAS 密钥对时出现错误！", ex);
            return null;
        }
    }

    /**
     * 对指定的密钥进行 Base64 编码
     * @param key 密钥
     * @return 返回密钥的 Base64 字符串形式
     */
    public static String encodeKey(Key key) {
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 对 SecretKey 进行 Base64 解码
     * @param secretKeyBase64 SecretKey 的 Base64 字符串形式
     * @param algorithm 算法
     * @return 返回 SecretKey
     */
    public static SecretKey decodeSecretKey(String secretKeyBase64, String algorithm) {
        byte[] encodedKey = Base64.decodeBase64(secretKeyBase64);
        return new SecretKeySpec(encodedKey, algorithm);
    }

    /**
     * 对 RSA 公钥进行 Base64 解码
     * @param publicKeyBase64 RSA 公钥的 Base64 字符串形式
     * @return 返回 RSA 公钥
     */
    public static PublicKey decodePublicKey(String publicKeyBase64) {
        try {
            byte[] encodedKey = Base64.decodeBase64(publicKeyBase64);
            KeySpec keySpec = new X509EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA, BouncyCastleProvider.PROVIDER_NAME);
//            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            return keyFactory.generatePublic(keySpec);
        }
        catch (Exception ex) {
            LOGGER.error("对 RSA 公钥进行 Base64 解码时出现错误！", ex);
            return null;
        }
    }

    /**
     * 对 RSA 私钥进行 Base64 解码
     * @param privateKeyBase64 RSA 私钥的 Base64 字符串形式
     * @return 返回 RSA 私钥
     */
    public static PrivateKey decodePrivateKey(String privateKeyBase64) {
        try {
            byte[] encodedKey = Base64.decodeBase64(privateKeyBase64);
            KeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA, BouncyCastleProvider.PROVIDER_NAME);
//            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            return keyFactory.generatePrivate(keySpec);
        }
        catch (Exception ex) {
            LOGGER.error("对 RSA 私钥进行 Base64 解码时出现错误！", ex);
            return null;
        }
    }

    /**
     * 加密数据（AES）
     * @param data 待加密的数据
     * @param key 用来对数据进行加密的密钥
     * @return 返回加密后的数据
     */
    public static byte[] encryptAes(byte[] data, Key key) {
        return encrypt(data, ALGORITHM_AES, key);
    }

    /**
     * 加密数据（RSA）
     * @param data 待加密的数据
     * @param key 用来对数据进行加密的密钥
     * @return 返回加密后的数据
     */
    public static byte[] encryptRsa(byte[] data, Key key) {
        return encrypt(data, ALGORITHM_RSA, key);
    }

    /**
     * 加密数据
     * @param data 待加密的数据
     * @param algorithm 加密算法
     * @param key 用来对数据进行加密的密钥
     * @return 返回加密后的数据
     */
    public static byte[] encrypt(byte[] data, String algorithm, Key key) {
        return cipher(data, Cipher.ENCRYPT_MODE, algorithm, key);
    }

    /**
     * 解密数据（AES）
     * @param data 待解密的数据
     * @param key 用来对数据进行解密的密钥
     * @return 返回解密后的数据
     */
    public static byte[] decryptAes(byte[] data, Key key) {
        return decrypt(data, ALGORITHM_AES, key);
    }

    /**
     * 解密数据（RSA）
     * @param data 待解密的数据
     * @param key 用来对数据进行解密的密钥
     * @return 返回解密后的数据
     */
    public static byte[] decryptRsa(byte[] data, Key key) {
        return decrypt(data, ALGORITHM_RSA, key);
    }

    /**
     * 获取 JavaScript 形式的公钥（用于浏览器端加密）
     * @return
     */
    public static String getJsPublicKey(PublicKey publicKey) {
        return ((RSAPublicKey) publicKey).getModulus().toString(16);
    }

    /**
     * 解密使用 JavaScript 加密的字符串
     * @param ciphertext
     * @param key
     * @return
     */
    public static String decryptRsaForJs(String ciphertext, Key key) {
        byte[] raw = decryptRsa(Hex.decode(ciphertext), key);
        // 标志位为0之后的是输入的有效字节
        int i = raw.length - 1;
        while (i > 0 && raw[i] != 0) {
            i--;
        }
        i++;
        byte[] data = new byte[raw.length - i];
        for (int j = i; j < raw.length; j++) {
            data[j - i] = raw[j];
        }

        return StringUtils.newStringUtf8(data);
    }

    /**
     * 解密数据
     * @param data 待解密的数据
     * @param algorithm 加密算法
     * @param key 用来对数据进行解密的密钥
     * @return 返回解密后的数据
     */
    public static byte[] decrypt(byte[] data, String algorithm, Key key) {
        return cipher(data, Cipher.DECRYPT_MODE, algorithm, key);
    }

    /**
     * 加密或解密数据
     * @param data 待处理的数据
     * @param mode 模式（加密 or 解密）
     * @param algorithm 加密算法
     * @param key 用来对数据进行加密或解密的密钥
     * @return 返回加密或解密后的数据
     */
    public static byte[] cipher(byte[] data, int mode, String algorithm, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm, BouncyCastleProvider.PROVIDER_NAME);
//            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(mode, key);

            int blockSize = cipher.getBlockSize();
            int outputSize = cipher.getOutputSize(blockSize);
            byte[] inBytes = new byte[blockSize];
            byte[] outBytes = new byte[outputSize];

            InputStream inputStream = new ByteArrayInputStream(data);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int readLength = 0;
            while ((readLength = inputStream.read(inBytes)) == blockSize) {
                int writeLength = cipher.update(inBytes, 0, blockSize, outBytes);
                outputStream.write(outBytes, 0, writeLength);
            }
            if(readLength > 0) {
                outBytes = cipher.doFinal(inBytes, 0, readLength);
            }
            else {
                outBytes = cipher.doFinal();
            }
            outputStream.write(outBytes);

            return outputStream.toByteArray();

//            return cipher.doFinal(data);
        }
        catch (Exception ex) {
            LOGGER.error("加密或解密数据时出现错误！", ex);
            return null;
        }
    }

    public static void main(String[] args) {
        SecretKey secretKey = generateAesSecretKey(256);
        saveSecretKey(secretKey, new File("D:/PK"));
    }
}