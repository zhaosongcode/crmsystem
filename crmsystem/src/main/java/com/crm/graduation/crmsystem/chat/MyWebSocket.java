package com.crm.graduation.crmsystem.chat;

import com.crm.graduation.crmsystem.service.other.CrmMessageService;
import com.crm.graduation.crmsystem.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@ServerEndpoint(value = "/websocket")
@Component
public class MyWebSocket {

    @Autowired
    private CrmMessageService crmMessageService;

    public static MyWebSocket myWebSocket;

    public MyWebSocket() {
    }

    @PostConstruct
    public void init() {
        myWebSocket = this;
        myWebSocket.crmMessageService = this.crmMessageService;
    }

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(MyWebSocket.class);

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static ConcurrentHashMap<String, MyWebSocket> webSocketSet = new ConcurrentHashMap<String, MyWebSocket>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法*/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        List<String> userIds = session.getRequestParameterMap().get("userId");
        String userId = userIds.get(0);
        webSocketSet.put(userId,this);
        addOnlineCount();           //在线数加1
        logger.info("有新连接加入！当前在线人数为" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        String userId = this.session.getRequestParameterMap().get("userId").get(0);
        webSocketSet.remove(userId,this);  //从set中删除
        subOnlineCount();           //在线数减1
        logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        //发送一对一消息，对消息进行处理
        String fromUserId = this.session.getRequestParameterMap().get("userId").get(0);
        sendToUser(message,fromUserId);
        //群发消息
        /*for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * 发生错误时调用
     @OnError
     **/
     public void onError(Session session, Throwable error) {
     System.out.println("发生错误");
     error.printStackTrace();
     }

    /**
     * 发送给制定用户
     */
    public void sendToUser(String message,String fromUserId) {
        String userId = message.split(",")[1];
        String sendMessage = message.split(",")[0];
        Date time = new Date();
        String timeS = Tools.tranTime(time);
        try {
            if(webSocketSet.get(userId) != null){
                webSocketSet.get(userId).sendMessage(sendMessage+","+fromUserId);
            }else{
                //不在线消息存数据库 状态未读
                message = message+","+fromUserId;
                myWebSocket.crmMessageService.saveUnReadMessages(message);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("发送异常!");
        }
    }

    /**
     * 群发公告
     * @param message
     * @throws IOException
     */
     public void sendMessage(String message) throws IOException {
     this.session.getBasicRemote().sendText(message);
     //this.session.getAsyncRemote().sendText(message);
     }

    /**
     * 发送在线集合
     * @param
     * @throws IOException
     */
    public void sendOnlineList(ConcurrentSkipListSet onlineList) throws IOException, EncodeException {
        //this.session.getBasicRemote().sendObject(onlineList);
        this.session.getAsyncRemote().sendObject(onlineList);
        //this.session.getAsyncRemote().sendText(message);
    }


     /**
      * 群发自定义消息
      * */
    /*public static void sendInfo(String message) throws IOException {
        for (MyWebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }*/

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        MyWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        MyWebSocket.onlineCount--;
    }
}
