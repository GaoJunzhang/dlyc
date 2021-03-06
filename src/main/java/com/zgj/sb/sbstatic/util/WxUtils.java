package com.zgj.sb.sbstatic.util;


import com.zgj.sb.sbstatic.model.WxConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

/**
 * @author liuweilong
 * @description
 * @date 2019/5/21 17:47
 */
@Slf4j
public class WxUtils {
    public static WxConfig getConfig(String jsapi_ticket, String link) {
        String nonceStr = createNonceStr();
        String timestamp = createTimestamp();
        String signature = getSignature(jsapi_ticket, nonceStr, timestamp, link);
        log.info("=======>>>>=======");
        log.info("nonceStr {}", nonceStr);
        log.info("timestamp {}", timestamp);
        log.info("jsapi_ticket {}", jsapi_ticket);
        log.info("link {}", link);
        log.info("signature {}", signature);
        log.info("=======>>>>=======");
        WxConfig wxConfig = new WxConfig();
        wxConfig.setNoncestr(nonceStr);
        wxConfig.setTimestamp(timestamp);
        wxConfig.setSignature(signature);
        return wxConfig;
    }

    /**
     * 生成nonce_str随机字符串
     */
    private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成timestamp
     */
    private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String getSignature(String jsapiTicket, String nonceStr, String timestamp, String url) {
        // 注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url="
                + url;

        String signature = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return signature;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
