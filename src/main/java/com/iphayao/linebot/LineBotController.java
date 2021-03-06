package com.iphayao.linebot;
import com.iphayao.linebot.*;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.LocationMessage;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.StickerMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.response.BotApiResponse;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
//import com.mendix.core.Core;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger; 

@Slf4j
@LineMessageHandler
public class LineBotController {
    @Autowired
    private LineMessagingClient lineMessagingClient;
    // Create a Logger 
    Logger logger 
        = Logger.getLogger( 
        		LineBotController.class.getName()); 

    @EventMapping
    public void handleTextMessage(MessageEvent<TextMessageContent> event) throws ClientProtocolException, IOException {
        logger.info(event.toString());
    	//Core.getLogger(getActionName()).info(event.toString());
        TextMessageContent message = event.getMessage();
        handleTextContent(event.getReplyToken(), event, message);
    }
    
    @EventMapping
    public void handleStickerMessage(MessageEvent<StickerMessageContent> event) {
        logger.info(event.toString());
        StickerMessageContent message = event.getMessage();
        reply(event.getReplyToken(), new StickerMessage(
                message.getPackageId(), message.getStickerId()
        ));
    }
    
    @EventMapping
    public void handleLocationMessage(MessageEvent<LocationMessageContent> event) {
        logger.info(event.toString());
        LocationMessageContent message = event.getMessage();
        reply(event.getReplyToken(), new LocationMessage(
                (message.getTitle() == null) ? "Location replied" : message.getTitle(),
                message.getAddress(),
                message.getLatitude(),
                message.getLongitude()
        ));
    }

    private void handleTextContent(String replyToken, Event event, 
                                   TextMessageContent content) throws ClientProtocolException, IOException {
        String text = content.getText();
        Callrest rest=new Callrest();
        String userId = event.getSource().getUserId();
        String reply="";
       // logger.info("Got text message from %s : %s", replyToken, text);

        switch (text) {
        
            case "Profile": {
                
                if(userId != null) {
                    lineMessagingClient.getProfile(userId)
                            .whenComplete((profile, throwable) -> {
                                if(throwable != null) {
                                    this.replyText(replyToken, throwable.getMessage());
                                    return;
                                }
                                this.reply(replyToken, Arrays.asList(
                                        new TextMessage("Display name: " + 
                                                        profile.getDisplayName()),
                                        new TextMessage("Status message: " + 
                                                        profile.getStatusMessage()),
                                        new TextMessage("User ID: " + 
                                                        profile.getUserId())
                                ));
                            });
                }
                break;
            }
            case "aaa":{
            	 this.replyText(replyToken,"aaaaaaaaaaaaaaa");
            	 break;
            }
            case "rest":{
            	
            	
            	rest.getCV("1");
                this.replyText(replyToken,"rest");
             
           	    break;
           }
            case "ONLINE ORDER":{
            	
            	
            	reply=rest.getOnlineOrder(userId);
            	this.replyText(replyToken,reply);
             
           	    break;
           }
            case "ORDER STATUS":{
            	
            	
            	reply=rest.getOrderStatus(userId);
            	this.replyText(replyToken,reply);
             
           	    break;
           }
            case "PRODUCT PRICE":{
            	
            	
            	reply=rest.getProductPrice(userId);
            	this.replyText(replyToken,reply);
             
           	    break;
           }
            case "CHECK BALANCE":{
            	
            	
            	reply=rest.getCheckBalance(userId);
            	this.replyText(replyToken,reply);
             
           	    break;
           }
            case "PAYMENT":{
            	
            	
            	reply=rest.getPayment(userId);
            	this.replyText(replyToken,reply);
              
           	    break;
           }
            case "CONTACT":{
            	
            	
            	reply=rest.getContact(userId);
                this.replyText(replyToken,reply);
                
           	    break;
           }
            default:
               // logger.info("Return echo message %s : %s", replyToken, text);
                this.replyText(replyToken,"กรุณาใส่คำให้ถูกต้อง");
        }
    }

    private void replyText(@NonNull  String replyToken, @NonNull String message) {
        if(replyToken.isEmpty()) {
            throw new IllegalArgumentException("replyToken is not empty");
        }

        if(message.length() > 1000) {
            message = message.substring(0, 1000 - 2) + "...";
        }
        this.reply(replyToken, new TextMessage(message));
    }

    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }

    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            BotApiResponse response = lineMessagingClient.replyMessage(
                    new ReplyMessage(replyToken, messages)
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    

}