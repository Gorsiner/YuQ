package wiki.IceCream.yuq.demo.event;

import com.IceCreamQAQ.Yu.annotation.Action;
import com.IceCreamQAQ.Yu.annotation.Event;
import com.IceCreamQAQ.Yu.annotation.EventListener;
import com.IceCreamQAQ.Yu.cache.EhcacheHelp;
import com.icecreamqaq.yuq.YuQ;
import com.icecreamqaq.yuq.event.GroupMessageEvent;
import com.icecreamqaq.yuq.event.GroupRecallEvent;
import com.icecreamqaq.yuq.event.MessageEvent;
import com.icecreamqaq.yuq.event.PrivateMessageEvent;
import com.icecreamqaq.yuq.message.Message;
import com.icecreamqaq.yuq.message.MessageItemFactory;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

@EventListener
public class OnMessageEvent {

    @Inject
    private YuQ yuq;

    /***
     * MessageItemFactory 用来创建 Message 的具体内容。
     */
    @Inject
    private MessageItemFactory mif;

    @Inject
    @Named("MessageSaved")
    private EhcacheHelp<Message> saves;

    private List<Message> messageList;

    /***
     * 当收到群聊消息时，本方法会被调用。
     * 事件会优先于控制器收到响应。
     * 事件可以被取消，当事件被取消之后，控制器将不会再响应。
     * @param event 事件
     */
    @Event
    public void onGroupMessage(GroupMessageEvent event) {
        Message message = event.getMessage();
        System.out.println("消息来自群：" + message.getGroup());
        System.out.println("消息来自群成员：" + message.getQq());
    }

    /***
     * 一个事件可以被重复注册多次，并可以通过指定优先级来让他们保持一定的先后顺序。
     * 本事件则简单地介绍了一个取消事件的方式。
     *
     * 当消息事件被取消后，后续将不会再进行控制器部分的响应了。
     * 也就是说，群号为 111 的群，永远不会响应到 Controller
     */
    @Event(weight = Event.Weight.low)
    public void onGroupMessageLow(GroupMessageEvent event) {
        if (111 == event.getMessage().getGroup()) event.setCancel(true);
    }

    private boolean recall = true;

    @Action("设置 防撤回 {flag}")
    public String menu2(boolean flag,long qq) {
        if (qq == 756228978){
            recall = flag;
            return "防撤回：" + flag;
        }
        return "对不起，你没有相关权限。";
    }

    @Event
    public void messageEvent(GroupMessageEvent event) {
        if (recall){
            Message message = event.getMessage();
            saves.set(message.getId().toString(),message);
            System.out.println("消息内容："+message.sourceMessage.toString());
        }
    }

    @Event
    public void recallEvent(GroupRecallEvent event) {
        if (recall){
            if (event.getSender() == event.getOperator()){
                Message message = saves.get(event.getMessageId()+"");
                yuq.sendMessage(message.newMessage().plus("群成员：").plus(mif.at(message.getQq())).plus("\n刚刚撤回了一条消息。\n消息内容为：\n").plus(message));
            }
        }
    }

    @Event
    public void hello(GroupMessageEvent event) {
        Message message = event.getMessage();
        if (message.sourceMessage.toString().contains("爪巴")) {
            yuq.sendMessage(message.newMessage().plus("爬就爬，我最会爬了。"));
        }
    }

    /***
     * 当收到私聊消息时，本方法会被调用。
     * 事件会优先于控制器收到响应。
     * 事件可以被取消，当事件被取消之后，控制器将不会再响应。
     * @param event 事件
     */
    @Event
    public void onPrivateMessage(PrivateMessageEvent event) {
        Message message = event.getMessage();
        System.out.println("消息来自：" + message.getQq());
    }

    /***
     * 当你注册了某个事件的父事件的时候，则这个父事件的所有子事件都会被响应。
     * 如 MessageEvent 是 PrivateMessageEvent 和 GroupMessageEvent 的父事件，
     * 则 PrivateMessageEvent 和 GroupMessageEvent 事件触发的时候，监听了 MessageEvent 事件的监听器都会受到响应。
     */
    @Event
    public void onMessage(MessageEvent event) {

    }

}
