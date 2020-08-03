package wiki.IceCream.yuq.demo.controller;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Before;
import com.IceCreamQAQ.Yu.job.JobManager;
import com.alibaba.fastjson.JSONObject;
import com.icecreamqaq.yuq.YuQ;
import com.icecreamqaq.yuq.annotation.GroupController;
import com.icecreamqaq.yuq.annotation.NextContext;
import com.icecreamqaq.yuq.annotation.PathVar;
import com.icecreamqaq.yuq.entity.Member;
import com.icecreamqaq.yuq.message.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import wiki.IceCream.yuq.demo.utils.GetTransApi;


import javax.inject.Inject;
import java.io.UnsupportedEncodingException;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.cn;

/***
 * GroupController 代表了，这个 Controller 将响应群消息。
 */
@GroupController
public class TestGroupController {

    /***
     * YuQ 接口是 YuQ Framework 向用户提供的一个便于使用的 API。
     * 通过注入 YuQ 来获得 YuQ 的实例，来调用 YuQ Framework 的绝大部分功能。
     * 如发送消息，撤回消息等等。
     */
    @Inject
    private YuQ yuq;

    /***
     * MessageFactory 可以用来创建 Message 对象，用来发送消息。
     */
    @Inject
    private MessageFactory mf;

    /***
     * MessageItemFactory 用来创建 Message 的具体内容。
     */
    @Inject
    private MessageItemFactory mif;

    /***
     * Before 则为具体的控制器的动作前置验证，也可以称作拦截器，负责在 Action 处理消息之前进行验证。
     *
     * Before 方法接收 0 - 多个参数，通常，您所写下的参数，将会以名称匹配来进行依赖注入。
     *   支持注入的名称 - 注入的内容
     *     qq - 发送消息的 QQ 账号
     *     group - 发送消息的 QQ 群号
     *     message - 具体的 Message 对象
     *     messageId - 消息 ID
     *     sourceMessage - 未经处理的源消息内容，则为具体的 Runtime 的消息内容
     *     actionContext - 当前消息的 ActionContext 对象
     *     以及，您 Before 传递回来的需要保存的对象。
     *
     * Before 方法可以接受任何类型的返回值，当您返回了一个值的时候，框架会帮您保存起来，名称则为将类名的第一个字母转化小写后的名字。
     *
     * Before 方法可以抛出异常，来作为验证失败的中断处理方法。
     * 当您抛出了一个 Message 类型的异常后，如果您没有设置任何接收的 QQ，或 QQ 群，那么我们将会将消息发送至当前消息来源者，如果您设置了接收对象，那么发送至您的接收对象。
     * 当您想中断处理链路，并且不进行任何返回的时候，您可以抛出 DoNone 类型的异常。
     *
     * 一个 Controller 类内，可以接受多个 Before，他们按照一定的顺序依次执行，当所有 Before 执行完成之后，将继续执行 Action。
     */
//    @Before
//    public void before(long qq) throws Message {
//        if (qq  != 756228978) throw mif.text("你没有使用该命令的权限！").toMessage();
//    }

    private boolean menuFlag = true;

    /***
     * Action 则为具体的控制器的动作，负责处理收到的消息。
     *
     * Action 方法接收 0 - 多个参数，通常，您所写下的参数，将会以名称匹配来进行依赖注入。
     *   支持注入的名称 - 注入的内容
     *     qq - 发送消息的 QQ 账号
     *     group - 发送消息的 QQ 群号
     *     message - 具体的 Message 对象
     *     messageId - 消息 ID
     *     sourceMessage - 未经处理的源消息内容，则为具体的 Runtime 的消息内容
     *     actionContext - 当前消息的 ActionContext 对象
     *     以及，您 Before 传递回来的需要保存的对象。
     *
     * Action 可接收方法可以接受任何类型的返回值，当您返回了一个值的时候，
     *   如果您返回的是 Message 类型的时候，我们会帮您发送这个消息，如果您没有设置任何接收的 QQ，或 QQ 群，那么我们将会将消息发送至当前消息来源者，如果您设置了接收对象，那么发送至您的接收对象。
     *   如果您返回了一个 String 类型的时候，我们会帮您构建一个 Message，并发送到当前消息的来源。
     *   如果您返回了一个 MessageItem 类型的时候，我们会帮您构建一个 Message，并发送到当前消息的来源。
     *   如果您返回的是其他类型，我们会帮您调用 toString 方法，并构建一个 Message，然后发送到当前消息的来源。
     *
     * Action 方法可以抛出异常，来返回一些信息。
     *   当您抛出了一个 Message 类型的异常后，如果您没有设置任何接收的 QQ，或 QQ 群，那么我们将会将消息发送至当前消息来源者，如果您设置了接收对象，那么发送至您的接收对象。
     *   当您想中断处理链路，并且不进行任何返回的时候，您可以抛出 DoNone 类型的异常。
     * @return
     */
    @Action("菜单")
    public Object menu(long qq) {
        if (menuFlag)
            return mif.at(qq).plus("，您好，当前仅有：\n" +
                    "\"随机头像 | 在线翻译\\n\" +\n" +
                    "                \"舔狗日记 | 网易云热评\\n\" +\n" +
                    "                \"随机语录 | 一份涩图\\n\" +\n" +
                    "                \"看　腿　 |60s看世界\"");
        return "菜单被禁用！";
    }

    /***
     * Action 内，不仅可以写单级指令，还可以写多级指令。
     * 最后的 {flag} 则代表了一个可变内容，他可以根据方法参数类型，自动映射为指定类型。
     */
    @Action("设置 菜单开关 {flag}")
    public String menu2(boolean flag,long qq) {
        if (qq == 756228978){
            menuFlag = flag;
            return "菜单开关：" + flag;
        }
        return "对不起，你没有相关权限。";
    }

    /***
     * 可以在路由内书写 { 名称 : 正则表达式 } 来动态匹配指令上的内容。
     * 如果你想匹配任意内容，则 : 及后续可以省略。示例：{color}
     * 本例子则代表只匹配单个文本。
     */
//    @Action("发个{color:.}包")
//    public String sendPackage(String color) {
//        return String.format("QQ%s包！", color);
//    }

    @Action("随机语录")
    public String message1(long qq) {
        HttpRequest httpRequest = HttpUtil.createGet("https://api.sunweihu.com/api/yan/api.php");
        String body = httpRequest.execute().body();
        return body;
    }

    @Action("网易云热评")
    public String hotEvaluate(long qq) {
        HttpRequest httpRequest = HttpUtil.createGet("https://xiaojieapi.cn/API/wyyrp.php");
        String body = httpRequest.execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String r = jsonObject.getString("data");
        JSONObject jo = JSONObject.parseObject(r);
        String song = jo.getString("song");
        String musicLink = jo.getString("Music Link");
        String content = jo.getString("content");
        return "网易云热评\n" +
                "来自音乐："+song+"\n" +
                "音乐链接："+musicLink+"\n" +
                "网抑症："+content;
    }

    @Action("舔狗日记")
    public String hotDog(long qq) {
        HttpRequest httpRequest = HttpUtil.createGet("https://xiaojieapi.cn/API/tiangou.php");
        String body = httpRequest.execute().body();
        JSONObject jsonObject = JSONObject.parseObject(body);
        String r = jsonObject.getString("text");
        return r;
    }

    /***
     * YuQ 可以将您发送的数字QQ号，或者 At 某人，智能转化为您所需要的内容。
     * 本处就将对象转化为 Member 的实例。
     * 通过调用 Member 的 ban 方法，可以将目标禁言一段时间。单位：秒。
     * 通过书写 Member 类型的 qq 参数，即可获取当前消息发送者的 Member 实例。
     * 通过调用 Member 的 isAdmin 方法，可以获取当前目标是否具有管理员权限。（管理员与群主都具有管理员权限）
     *
     * 本 Action 的作用，如果发送者是管理员，就将目标禁言一段时间，如果发送人不是管理员，就将自己禁言一段时间。
     */
    /*@Action("禁言 {sb} {time}")
    public String ban(Member sb, Member qq, int time) {
        if (time < 60) time = 60;
        if (qq.isAdmin()) {
            sb.ban(time);
            return "好的";
        }
        qq.ban(time);
        return "您没有使用该命令的权限！为了防止恶意操作，你已被禁言相同时间。";
    }*/

    @Inject
    private JobManager jobManager;

    /***
     * 我们可以通过注入 JobManager 快速，动态的创建定时与时钟任务。
     * 写在路由中的 {time} 与整级路由 {nr} 最大的不同，就是写在路由中的，只能用 String 类型接受，并不能智能匹配成其他类型。
     */
    @Action("{time}秒后说 {nr}")
    public Object timeSend(String time, MessageItem nr, Message message) {
        Message nm =message.newMessage().plus(nr);
        jobManager.registerTimer(() -> yuq.sendMessage(nm), Integer.parseInt(time) * 1000);
        return "好的";
    }

    @Action("看腿")
    public Image leg(long qq){
        HttpRequest httpRequest = HttpUtil.createGet("http://47.94.135.214/sjmt.php");
        String body = httpRequest.execute().body();
        return mif.image(body);
    }

    //@的使用
    /*@Action("请艾特我")
    public Message message2(long qq){
        return mif.at(qq).plus("有何贵干???");
    }*/
    @Action("随机男头")
    public Image nanX(long qq){
        return mif.image("https://api.sunweihu.com/api/sjtx/api.php?lx=a1");
    }

    @Action("随机女头")
    public Image nvX(long qq){
        return mif.image("https://api.sunweihu.com/api/sjtx/api.php?lx=b1");
    }

    /*@Action("我要一份色图")
    public Image setImg(long qq){
        return mif.image("https://api.iheit.com/pixiv/bookmarks");
    }
*/
    @Action("动漫头像")
    public Image dongX(long qq){
        return mif.image("https://api.sunweihu.com/api/sjtx/api.php?lx=c1");
    }

    @Action("动漫女头")
    public Image nvDongX(long qq){
        return mif.image("https://api.sunweihu.com/api/sjtx/api.php?lx=c2");
    }

    @Action("动漫男头")
    public Image nanDongX(long qq){
        return mif.image("https://api.sunweihu.com/api/sjtx/api.php?lx=c3");
    }

    @Action("哈哈")
    public Image image(long qq){
        return mif.image("https://s1.ax1x.com/2020/06/13/tvcIHK.jpg");
    }


    @Action("单身狗")
    public Image dog(long qq){
        return mif.image("https://s1.ax1x.com/2020/06/13/tvQAdU.jpg");
    }


    @Action("深夜放毒")
    public Image light(long qq){
        return mif.image("https://s1.ax1x.com/2020/06/13/tv2FIO.jpg");
    }

    @Action("整点好康的")
    public Image erciyuan(long qq){
        return mif.image("https://api.vvhan.com/api/acgimg");
    }

    @Action("随机美女")
    public Image beautiful(long qq){
        String url = "https://uploadbeta.com/api/pictures/random/?key=%E6%8E%A8%E5%A5%B3%E9%83%8E";
        return mif.image(url);
    }

//    @Action("发个红包")
//    public Object packet(long qq){
//        return mif.jsonEx("{\"app\":\"com.tencent.cmshow\",\"desc\":\"\",\"view\":\"game_redpacket\",\"ver\":\"1.0.3.5\",\"prompt\":\"QQ红包\",\"appID\":\"\",\"sourceName\":\"\",\"actionData\":\"\",\"actionData_A\":\"\",\"sourceUrl\":\"\",\"meta\":{\"redPacket\":{\"destUrl\":\".2.15844517927 .com\",\"msg\":\"QQ红包\",\"posterUrl\":\"\\/qqshow\\/admindata\\/comdata\\/vipActTpl_mobile_zbltyxn\\/dddb247a4a9c6d34757c160f9e0b6669.gif\"}},\"config\":{\"forward\":1},\"text\":\"\",\"sourceAd\":\"\"}");
//    }

    @Action("复读我下一句话")
    @NextContext("recallMyNextMessage")
    public String rel(){
        return "您请说：";
    }


    @Action("recallMyNextMessage")
    public Message rel2(Message message){
        return message;
    }

    @Action("准备json")
    @NextContext("recallJsonNextMessage")
    public String waitJson(){
        return "您请说";
    }

    @Action("recallJsonNextMessage")
    public Object json(Message message){
        return mif.jsonEx(((Text)message.getBody().get(0)).getText());
    }


    @Action("翻译菜单")
    public Object transMenu(){
        return mif.text("目前支持的翻译语种：日语,中文,英语,粤语,文言文,韩语,法语,西班牙语,泰语,阿拉伯语," +
                "俄语,葡萄牙语,德语,意大利语,越南语,希腊语");
    }

    @Action("翻译")
    @NextContext("transMessage")
    public Object trans(){
        return "请输入需要翻译的语种以及需要翻译的句子\n 如： \n 日语：变态";
    }

    @Action("transMessage")
    public Object transMessage(Message message) throws UnsupportedEncodingException {
        String s = message.sourceMessage.toString();
        String[] split = s.split("]");
        String messageBody = new String(split[1].getBytes(),"UTF-8");
        System.out.println("messageBody:"+messageBody);
        String[] split1 = messageBody.split("：");
        String to = "";
        String toStr = split1[0];
        System.out.println("toStr"+toStr);
        if (toStr.equals("日语")){
            to="jp";
        }
        if (toStr.equals("中文")){
            to="zh";
        }
        if (toStr.equals("英语")){
            to="en";
        }
        if (toStr.equals("粤语")){
            to="yue";
        }
        if (toStr.equals("文言文")){
            to="wyw";
        }
        if (toStr.equals("韩语")){
            to="kor";
        }
        if (toStr.equals("法语")){
            to="fra";
        }
        if (toStr.equals("西班牙语")){
            to="spa";
        }
        if (toStr.equals("泰语")){
            to="th";
        }
        if (toStr.equals("阿拉伯语")){
            to="ara";
        }
        if (toStr.equals("俄语")){
            to="ru";
        }
        if (toStr.equals("葡萄牙语")){
            to="pt";
        }
        if (toStr.equals("德语")){
            to="de";
        }
        if (toStr.equals("意大利语")){
            to="it";
        }
        if (toStr.equals("越南语")){
            to="vie";
        }
        if (toStr.equals("希腊语")){
            to="el";
        }
        System.out.println("to:"+to);
        String query = split1[1];
        System.out.println("query:"+query);
        String trans = GetTransApi.getTrans(query, to);
        System.out.println("trans:"+trans);
        JsonParser jp = new JsonParser();
        //将json字符串转化成json对象
        JsonObject jo = jp.parse(trans).getAsJsonObject();
        String resultStr = jo.get("trans_result").toString();
        String[] result = resultStr.split("\"dst\":");
        return mif.text("翻译结果为：[{"+result[1]);
    }

    @Action("60s看世界")
    public Object watchWorld() {
        return mif.image("https://xiaojieapi.cn/API/60s.php?name=小艾");
    }

    /***
     * NextContext 注解用来声明完成之后进入某个上下文。
     * 这是一个用来进入上下文的 Action，当然，他与普通的 Action 没有什么区别。
     * 他可以完成普通 Action 的所有功能。
     * 只是当他在正常完成之后，会帮你把上下文自动切换到 NextContext 中声明的内容。
     *
     * 正常完成则为，当 Action 方法没有产生任何异常时，Action 方为正常完成。
     */
    @Action("绑定手机号")
    @NextContext("bindPhone")
    public void bindPhone(long qq) throws Message {
        if (qq  == 756228978) throw mif.text("您无需绑定手机号码。").toMessage();
    }


}
