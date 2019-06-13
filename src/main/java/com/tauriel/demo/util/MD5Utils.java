package com.tauriel.demo.util;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	/**
    *
    * @param plainText
    *            明文
    * @return 32位密文
    */
   public static String cell32(String plainText) {
       String re_md5 = new String();
       try {
           MessageDigest md = MessageDigest.getInstance("MD5");
           md.update(plainText.getBytes());
           byte[] b = md.digest();

           int i;

           StringBuffer buf = new StringBuffer("");
           for (int offset = 0; offset < b.length; offset++) {
               i = b[offset];
               if (i < 0) {
                   i += 256;
               }
               if (i < 16) {
                   buf.append("0");
               }
               buf.append(Integer.toHexString(i));
           }

           re_md5 = buf.toString();

       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       }
       return re_md5;
   }

   /**
    * 取32的中间16位
    * @param plainText
    * @return
    */
   public static String md5_16(String plainText){
   	return StringUtils.substring(cell32(plainText), 8, 24);
   }


}
