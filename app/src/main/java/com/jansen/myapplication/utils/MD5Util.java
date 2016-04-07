package com.jansen.myapplication.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 加密
* @Description:  
* @author LiLong
* @date 2014-10-24 下午05:45:24 
* @UpdateData 2014-10-24 下午05:45:24 by_
 */
public class MD5Util
{

	public final static String getRequestMD5Key(String pathUrl,String jsonStr,String key,String time){
		return null;
	}

    public final static String MD5_32(String plainText) {
        return MD5_32(plainText, "UTF-8");
    }

    /**
	 * 32位加�?
	* @author LiLong
	* @param  plainText
	* @return String
	* @UpdateData 2014-10-24 下午05:46:03 by_
	 */
	public final static String MD5_32(String plainText, String encode)
	{
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes(encode));
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            re_md5 = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return re_md5;
    }

	public final static String MD5_16(String s) {
        return MD5_32(s).substring(8, 24);
    }

	// PATH+PARAMER+KEY+TIME
	// 拼接�?��加密的字�?
	public static String encryptionUrl()
	{

		return null;
	}

	public static void printTest()
	{
        /**
         * 		WnLogsUtils.d(MD5Util
         .MD5_16("login?name=l&ps=1&key=123&time=20140526"));
         WnLogsUtils.d(MD5Util.MD5_32("加密"));
         */

	}
}
