package com.example.demo.config.websocket;
 
import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.config.common.BaseContant;
import com.example.demo.config.utils.SpringUtil;
import com.example.demo.myPostBar.user.entity.TUserNews;
import com.example.demo.myPostBar.user.mapper.TUserMapper;
import com.example.demo.myPostBar.user.mapper.TUserNewsMapper;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 *
 * @ServerEndpoint 这个注解有什么作用？
 *
 * 这个注解用于标识作用在类上，它的主要功能是把当前类标识成一个WebSocket的服务端
 * 注解的值用户客户端连接访问的URL地址
 *
 */
 
@Component
@ServerEndpoint(value="/websocket/{name}",encoders = { ServerEncoder.class })
@MapperScan("com.example.demo.myPostBar.user.mapper")
public class Websocket {
 
	@Autowired
	private TUserNewsMapper tUserNewsMapper;
    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;
 
     /**
     * 标识当前连接客户端的用户名
     */
    private String name;
 
    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String,Websocket> webSocketSet = new ConcurrentHashMap<>();
 
 
    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name,this);
        System.out.println("[WebSocket] 连接成功，当前连接人数为：={}"+webSocketSet.size());
    }
 
 
    @OnClose
    public void OnClose(){
        webSocketSet.remove(this.name);
        System.out.println("[WebSocket] 退出成功，当前连接人数为：="+webSocketSet.size());
    }
 
    @OnMessage
    public void OnMessage(String message){
    	if("heartCheck".equals(message)) {//如果发过来的消息是心跳，那么回一个心跳
    		GroupSending("heartCheck");
    	}
    	System.out.println("收到消息:"+message);
    	System.out.println("发送给"+name+"的消息"+message);
        //判断是否需要指定发送，具体规则自定义
//        if(message.indexOf("TOUSER") == 0){
//            String name = message.substring(message.indexOf("TOUSER")+6,message.indexOf(";"));
//            AppointSending(name,message.substring(message.indexOf(";")+1,message.length()));
//        }else{
            GroupSending(message);
//        }
 
    }
 
    /**
                  * 群发
     * @param message
     */
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
 
    /**
     * 指定发送
     * @param name
     * @param message
     */
    public void AppointSending(String name,TUserNews message){
        try {
        	message.setStatus(BaseContant.NEWS_STATUS_NOT_READED);
        	message.setCreateDate(LocalDateTime.now());
        	SpringUtil.getBean(TUserNewsMapper.class).insert(message);
            webSocketSet.get(name).session.getBasicRemote().sendObject(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 指定发送,上线时拿到所有未读消息发送
     * @param name
     * @param message
     */
//    public void AppointSendingBatch(String name,List<TUserNews> message){
//        try {
//            webSocketSet.get(name).session.getBasicRemote().sendObject(message);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
}