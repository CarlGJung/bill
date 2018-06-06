
import top.carljung.bill.wx.WXBizDataCrypt;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 *
 * @author wangchao
 */
public class WXDecoderTest {
       @Test
    public void test(){
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String encryptedData = 	
                "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM"+
	"QmRzooG2xrDcvSnxIMXFufNstNGTyaGS"+
	"9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+"+
	"3hVbJSRgv+4lGOETKUQz6OYStslQ142d"+
	"NCuabNPGBzlooOmB231qMM85d2/fV6Ch"+
	"evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6"+
	"/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw"+
	"u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn"+
	"/Hz7saL8xz+W//FRAUid1OksQaQx4CMs"+
	"8LOddcQhULW4ucetDf96JcR3g0gfRK4P"+
	"C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB"+
	"6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns"+
	"/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd"+
	"lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV"+
	"oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG"+
	"20f0a04COwfneQAGGwd5oa+T8yO5hzuy"+
	"Db/XcxxmK01EpqOyuxINew==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        String decrypted = WXBizDataCrypt.decryptDataBase64(encryptedData, sessionKey, iv);
        System.out.println(decrypted);
    }
    
    
    @Test
    public void testEncode() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterSpecException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        String appId = "wx4f4bc4dec97d474b";
        String sessionKey = "tiihtNczf5v6AKRyjwEUhQ==";
        String iv = "r7BXXKkLb8qrSNn05n0qiA==";
        String data = "data = {"
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
//        byte[] ivByte = Base64.decodeBase64(iv);
//        byte[] keyByte = Base64.decodeBase64(sessionKey);
//        Security.addProvider(new BouncyCastleProvider());
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
//        AlgorithmParameters algoParam = AlgorithmParameters.getInstance("AES");
//        algoParam.init(new IvParameterSpec(ivByte));
//        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyByte, "AES"), algoParam);
//        byte[] result = cipher.doFinal(data.getBytes("UTF-8"));
//        System.out.println(Base64.encodeBase64String(result));
        String result = WXBizDataCrypt.encodeDataBase64(data, sessionKey, iv);
        String decrypted = WXBizDataCrypt.decryptDataBase64(result, sessionKey, iv);
        System.out.println(decrypted);
    }
}
