
/**
 * @Title RsaUtil.java
 * @Description TODO
 * @author bluecrush
 * @date 2016-12-13下午5:00:41
 * @version v1.1
 */
package com.tauriel.demo.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author bluecrush
 * @version v1.1
 * @ClassName: RsaUtil
 * @Description:
 * @date 2016-12-13 下午5:00:41
 */
@Component
@ComponentScan
public class RsaUtil {

    /**
     * 公钥
     */
    private static PublicKey publicKey;

    /**
     * 私钥
     */
    private static PrivateKey privateKey;

    /**
     * 工具初始化
     *
     * @param storepass
     * @param alias
     * @param keypass
     * @throws Exception
     */
    public RsaUtil(@Value("${rsa.storepass}") String storepass, @Value("${rsa.alias}") String alias, @Value("${rsa.keypass}") String keypass) {
        try (InputStream fileInputStream = this.getClass().getClassLoader().getResourceAsStream("SmartOpen.keystore");) {
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(fileInputStream, storepass.toCharArray());      // 加载证书
            Certificate cer = ks.getCertificate(alias);             // alias为条目的别名
            publicKey = cer.getPublicKey();
            privateKey = (PrivateKey) ks.getKey(alias, keypass.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * @param data
     * @throws Exception
     * @Title:pprivateDecryption
     * @Description:TODO 公钥加密->私钥解密 使用私钥进行解密
     * @return:String
     * @author:blueCrush
     * @date:2016-12-13下午5:17:55
     */
    public static String privateDecryption(String data) throws Exception {
        String result = null;
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bt = cipher.doFinal(Base64.decodeBase64(data));

        result = new String(bt, "UTF-8");

        return result;
    }

    /**
     * @param data
     * @throws Exception
     * @Title:privateEncode
     * @Description:TODO 私钥加密->公钥解密 使用私钥进行加密
     * @return:String
     * @author:blueCrush
     * @date:2016-12-13下午5:13:48
     */
    public static String privateEncryption(String data) throws Exception {
        String result = null;
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] bt = cipher.doFinal(data.getBytes("UTF-8"));

        result = Base64.encodeBase64String(bt);

        return result;
    }

    /**
     * @param data
     * @throws Exception
     * @Title:publicDecryption
     * @Description:TODO 私钥加密->公钥解密 使用公钥进行解密
     * @return:String
     * @author:blueCrush
     * @date:2016-12-13下午5:17:55
     */
    public static String publicDecryption(String data) throws Exception {
        String result = null;
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        byte[] bt = cipher.doFinal(Base64.decodeBase64(data));

        result = new String(bt, "UTF-8");

        return result;
    }

    /**
     * @param data
     * @throws Exception
     * @Title:privateEncode
     * @Description:TODO 公钥加密->私钥解密 使用公钥进行加密
     * @return:String
     * @author:blueCrush
     * @date:2016-12-13下午5:13:48
     */
    public static String publicEncryption(String data) throws Exception {
        String result = null;
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");

        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] bt = cipher.doFinal(data.getBytes("UTF-8"));

        result = Base64.encodeBase64String(bt);

        return result;
    }
}


