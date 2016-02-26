package util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	/** 
	 * 加密 
	 *  
	 * @param content 需要加密的内容 
	 * @param password  加密密码 
	 * @return 
	 */  
	public static String encrypt(String content, String password) {  
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"),
					"AES");// 使用SecretKeySpec类来根据一个字节数组构造一个
							// SecretKey,，而无须通过一个（基于provider的）SecretKeyFactory.
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
																		// 为创建
																		// Cipher
																		// 对象，应用程序调用
																		// Cipher
																		// 的
																		// getInstance
																		// 方法并将所请求转换																		
			// 的名称传递给它。还可以指定提供者的名称（可选）。
			byte[] byteContent = content.getBytes("UTF-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent); // 按单部分操作加密或解密数据，或者结束一个多部分操作。数据将被加密或解密（具体取决于此
															// Cipher 的初始化方式）。
			return byte2hex(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;  
	}  
	
	/**解密 
	 * @param content  待解密内容 
	 * @param password 解密密钥 
	 * @return 
	 */  
	public static String decrypt(String content, String password) {  
		try {
			SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"),
					"AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(hex2byte(content.getBytes("UTF-8")));
			return new String(result); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;  
	}  
	
	/**将二进制转换成16进制 
	 * @param buf 
	 * @return 
	 */  
	private static String byte2hex(byte[] b) { // 一个字节的数，
		// 转成16进制字符串
		String hs = "";
		String tmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			tmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (tmp.length() == 1) {
				hs = hs + "0" + tmp;
			} else {
				hs = hs + tmp;
			}
		}
		tmp = null;
		return hs.toUpperCase(); // 转成大写

	}
	
	/**将16进制转换成二进制
	 * @param buf 
	 * @return 
	 */ 
	private static byte[] hex2byte(byte[] b) {   if ((b.length % 2) != 0) {    throw new IllegalArgumentException("长度不是偶数");   }      byte[] b2 = new byte[b.length / 2];      for (int n = 0; n < b.length; n += 2) {    String item = new String(b, n, 2);    b2[n / 2] = (byte) Integer.parseInt(item, 16); }      return b2;  }
	
}
