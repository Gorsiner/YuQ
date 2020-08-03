package wiki.IceCream.yuq.demo.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: YuQ-Mirai-Demo
 * @author: Gorsiner
 * @create: 2020-07-03 14:49
 **/
public class CleverApi {
    private static final String serverUrl ="https://api.ai.qq.com/fcgi-bin/nlp/nlp_textchat";
    private static final String app_id = "2151759854";
    private static final String session = "xzMmKCB1zZklazrE";
    private static final int time_stamp = 1493468759;


    public static void main(String[] args) {
        String query = "";
        String securityKey = "";

        // 随机数
        String nonce_str = String.valueOf(System.currentTimeMillis());

        String src = app_id + query + nonce_str + securityKey; // 加密前的原文
        String sign = MD5.md5(src);
    }
}
