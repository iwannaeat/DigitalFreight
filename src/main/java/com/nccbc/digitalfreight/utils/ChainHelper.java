package com.nccbc.digitalfreight.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @program: DigitalFreight
 * @description: create UUID
 * @author: Haochen Ren
 * @create: 2023-03-07 10:23
 **/
public class ChainHelper {
    static public String createUUID(int length) {
        Random random = new Random();
        String str = "zxcvbnmlhfdsaqwertuio1234567890";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(31);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

     static public int getDifferHour(Date endDate) throws ParseException {
         SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date startDate = sdf.parse("2023-03-07 08:00:00");

         long dayM = 1000 * 24 * 60 * 60;
         long hourM = 1000 * 60 * 60;
         long differ = endDate.getTime() - startDate.getTime();
         long hour = differ % dayM / hourM + 24 * (differ / dayM);
         return Integer.parseInt(String.valueOf(hour));
    }

    static public String sha_func(final String strText, final String strType)
    {
        // 返回值
        String strResult = null;

        // 是否是有效字符串
        if (strText != null && strText.length() > 0)
        {
            try
            {
                // SHA 加密开始
                // 创建加密对象 并傳入加密類型
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                // 传入要加密的字符串
                messageDigest.update(strText.getBytes());
                // 得到 byte 類型结果
                byte byteBuffer[] = messageDigest.digest();

                // 將 byte 轉換爲 string
                StringBuffer strHexString = new StringBuffer();
                // 遍歷 byte buffer
                for (int i = 0; i < byteBuffer.length; i++)
                {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1)
                    {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                // 得到返回結果
                strResult = strHexString.toString();
            }
            catch (NoSuchAlgorithmException e)
            {
                e.printStackTrace();
            }
        }

        return strResult;
    }
}
