package com.analytic.portal.common.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.analytic.portal.common.sys.GlobalConstants;

/**
 * DES
 * @author Boger
 */
public class DESEncode {
	private static AlgorithmParameterSpec iv=null;
	private static Key key=null;
	private static DESEncode instance=new DESEncode();
	
	/**
	 * DES构造器
	 * Boger
	 * 2016年5月13日上午10:34:19
	 */
	public DESEncode(){
		try {
			byte[] DESKEY=GlobalConstants.SYS_DES_KEY.getBytes("UTF-8");
			byte[] DESIV = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xAB, (byte) 0xCD, (byte) 0xEF};
			DESKeySpec keySpec=new DESKeySpec(DESKEY);
			iv=new IvParameterSpec(DESIV);
			SecretKeyFactory factory=SecretKeyFactory.getInstance(GlobalConstants.SYS_DES_MODEL);
			key=factory.generateSecret(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 实例
	 * @return
	 * @throws Exception
	 * Boger
	 * 2016年5月13日上午11:35:02
	 */
	public static DESEncode getInstance() throws Exception{
		return instance;
	}
	
	/**
	 * DES加密
	 * @param toEncrypt
	 * @param key
	 * @return
	 * Boger
	 * 2016年5月13日上午10:11:27
	 */
	public static String Encrypt(String toEncrypt) throws Exception{
		String result="";
		BASE64Encoder encoder=new BASE64Encoder();
		
		Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key,iv);
		byte[] encryptByte=cipher.doFinal(toEncrypt.getBytes());
		result=encoder.encode(encryptByte);
		
		return result;
	}
	
	/**
	 * DES解密
	 * @param toDecrypt
	 * @param key
	 * @return
	 * Boger
	 * 2016年5月13日上午10:11:57
	 */
	public static String Decrypt(String toDecrypt) throws Exception{
		String result="";
		BASE64Decoder decoder=new BASE64Decoder();
		
		Cipher cipher=Cipher.getInstance("DES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key,iv);
		byte[] encryptByte=cipher.doFinal(decoder.decodeBuffer(toDecrypt));
		result=new String(encryptByte,"UTF-8");
		
		return result;
	}
	
}
