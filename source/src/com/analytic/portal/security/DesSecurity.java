package com.analytic.portal.security;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class  DesSecurity {
	BASE64Encoder encoder;

	BASE64Decoder decoder;

	private Cipher enCipher;

	private Cipher deCipher;
	
	protected abstract String getkey();
	
	protected abstract String getIv();
	
	/**
	 * @param key 加密密钥
	 * @param iv 初始化向量
	 * @throws Exception
	 */
	protected DesSecurity() throws Exception {
		if (getkey() == null) {
			throw new NullPointerException("Parameter is null!");
		}
		initCipher(getkey().getBytes(), getIv().getBytes());
	}

	private void initCipher(byte[] secKey, byte[] secIv) throws Exception {
		
		// 获得DES密钥
		DESKeySpec dks = new DESKeySpec(secKey);
		/*MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(secKey);
		DESKeySpec dks = new DESKeySpec(md.digest());*/
		// 获得DES加密密钥工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 生成加密密钥
		SecretKey key = keyFactory.generateSecret(dks);
		// 创建初始化向量对象
		IvParameterSpec iv = new IvParameterSpec(secIv);
		AlgorithmParameterSpec paramSpec = iv;
		// 为加密算法指定填充方式，创建加密会话对象
		enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 为加密算法指定填充方式，创建解密会话对象
		deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		// 初始化加解密会话对象
		enCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		deCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		encoder = new BASE64Encoder();
		decoder = new BASE64Decoder();
	}

	/**
	 * 加密数据
	 * @param data 待加密二进制数据
	 * @return 经BASE64编码过的加密数据
	 * @throws Exception
	 */
	public String encrypt(byte[] data) throws Exception {
		return encoder.encode(enCipher.doFinal(data)).replaceAll("\n", "").replaceAll("\r", "");
	}

	/**
	 * 解密数据
	 * @param data 待解密字符串（经过BASE64编码）
	 * @return 解密后的二进制数据
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public byte[] decrypt(String data) throws Exception {
		String dfd=(new String(decoder.decodeBuffer(data),"utf-8"));
		return deCipher.doFinal(decoder.decodeBuffer(data));
	}

}