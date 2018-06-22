
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

/**
 *
 * @author ngh
 * AES128 算法
 *
 * CBC 模式
 *
 * PKCS7Padding 填充模式
 *
 * CBC模式需要添加一个参数iv
 *
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class AESPKCS7Test {
 // 算法名称
 final String KEY_ALGORITHM = "AES";
 // 加解密算法/模式/填充方式
 final String algorithmStr = "AES/CBC/PKCS7Padding";
 //
 private Key key;
 private Cipher cipher;
 boolean isInited = false;
 
 byte[] iv = Base64.decodeBase64("r7BXXKkLb8qrSNn05n0qiA==");
 public void init(byte[] keyBytes) {

  // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
  int base = 16;
  if (keyBytes.length % base != 0) {
   int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
   byte[] temp = new byte[groups * base];
   Arrays.fill(temp, (byte) 0);
   System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
   keyBytes = temp;
  }
  // 初始化
  Security.addProvider(new BouncyCastleProvider());
  // 转化成JAVA的密钥格式
  key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
  try {
   // 初始化cipher
   cipher = Cipher.getInstance(algorithmStr, "BC");
  } catch (NoSuchAlgorithmException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (NoSuchPaddingException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  } catch (NoSuchProviderException e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
 }
 /**
  * 加密方法
  *
  * @param content
  *            要加密的字符串
  * @param keyBytes
  *            加密密钥
  * @return
  */
 public byte[] encrypt(byte[] content, byte[] keyBytes) {
  byte[] encryptedText = null;
  init(keyBytes);
  System.out.println("IV：" + new String(iv));
  try {
   cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
   encryptedText = cipher.doFinal(content);
  } catch (Exception e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return encryptedText;
 }
 /**
  * 解密方法
  *
  * @param encryptedData
  *            要解密的字符串
  * @param keyBytes
  *            解密密钥
  * @return
  */
 public byte[] decrypt(byte[] encryptedData, byte[] keyBytes) {
  byte[] encryptedText = null;
  init(keyBytes);
  System.out.println("IV：" + new String(iv));
  try {
   cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
   encryptedText = cipher.doFinal(encryptedData);
  } catch (Exception e) {
   // TODO Auto-generated catch block
   e.printStackTrace();
  }
  return encryptedText;
 }
 
 @Test
 public void test() {
  AESPKCS7Test aes = new AESPKCS7Test();
//   加解密 密钥
  String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
  String content = "data = {"
                    +"\"nickName\": \"Band\","
                    +"\"gender\": 1,"
                    +"\"language\": \"zh_CN\","
                    +"\"city\": \"Guangzhou\","
                    +"\"province\": \"Guangdong\","
                    +"\"country\": \"CN\","
                    +"\"avatarUrl\": \"http://wx.qlogo.cn/mmopen/vi_32/aSKcBBPpibyKNicHNTMM0qJVh8Kjgiak2AHWr8MHM4WgMEm7GFhsf8OYrySdbvAMvTsw3mo8ibKicsnfN5pRjl1p8HQ/0\","
                    +"\"unionId\": \"ocMvos6NjeKLIBqg5Mr9QjxrP1FA\","
                    +"\"watermark\": {"
                        +" \"timestamp\": 1477314187,"
                        +"\"appid\": \"wx4f4bc4dec97d474b\""
                    +"}"
                +"}";
  // 加密字符串
  System.out.println("加密前的：" + content);
  System.out.println("加密密钥：" + sessionKey);
  // 加密方法
  byte[] enc = aes.encrypt(content.getBytes(), Base64.decodeBase64(sessionKey));
  System.out.println("加密后的内容：" + new String(Base64.encodeBase64(enc)));
  // 解密方法
  byte[] dec = aes.decrypt(enc, Base64.decodeBase64(sessionKey));
  System.out.println("解密后的内容：" + new String(dec));
 }

}
