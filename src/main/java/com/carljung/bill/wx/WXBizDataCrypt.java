package com.carljung.bill.wx;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author wangchao
 */
public class WXBizDataCrypt {
    private static volatile boolean providerAdded = false;
    private static String algorithm = "AES";
    
    public static String decryptDataBase64(String encryptedData, String sessionKey, String initializtionVector){
        return decryptData(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(initializtionVector));
    }
    
    public static String decryptData(byte[] encryptedData, byte[] secretKey, byte[] initializationVector){
        addProvider();        
        try {
            Cipher cipher = Cipher.getInstance(algorithm + "/CBC/PKCS7Padding");
            Key secretKeySpec = new SecretKeySpec(secretKey, algorithm);
            AlgorithmParameters algoParam = AlgorithmParameters.getInstance(algorithm);
            algoParam.init(new IvParameterSpec(initializationVector));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, algoParam);
            byte[] result = cipher.doFinal(encryptedData);
            
            return new String(result, "UTF-8");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidParameterSpecException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String encodeDataBase64(String content, String key, String iv){
        addProvider();
        byte[] result = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            AlgorithmParameters algoParam = AlgorithmParameters.getInstance("AES");
            algoParam.init(new IvParameterSpec(Base64.decodeBase64(iv)));
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.decodeBase64(key), "AES"), algoParam);
            result = cipher.doFinal(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidParameterSpecException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(WXBizDataCrypt.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Base64.encodeBase64String(result);
    }
    private static void addProvider(){
        if (!providerAdded) {
            Security.addProvider(new BouncyCastleProvider());
            providerAdded = true;
        }
    }
}
