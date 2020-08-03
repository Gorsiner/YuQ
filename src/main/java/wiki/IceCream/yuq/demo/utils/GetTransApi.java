package wiki.IceCream.yuq.demo.utils;

/**
 * @program: YuQ-Mirai-Demo
 * @author: Gorsiner
 * @create: 2020-06-22 17:23
 **/
public class GetTransApi {

    private static final String APP_ID = "20200622000503650";
    private static final String SECURITY_KEY = "v5nwP5ESn8LG9shjEt3a";

    public static String getTrans(String query,String to) {
        TransApi api = new TransApi(APP_ID, SECURITY_KEY);
        String transResult = api.getTransResult(query, "auto", to);
        return transResult;
    }
}
