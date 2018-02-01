package tools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util {

	public String encrypt(String str) {
		String result = "";
		byte[] b = null;
		try {
			b = str.getBytes();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (b != null) {
			result = new BASE64Encoder().encode(b);
		}

		return result;
	}

	public String decrypt(String str) {
		String result = "";
		byte[] b = null;
		try {
           b=new BASE64Decoder().decodeBuffer(str);
           result=new String(b,"utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
