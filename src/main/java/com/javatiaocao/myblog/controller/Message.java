package com.javatiaocao.myblog.controller;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 442244L ;

    private String sender ;
    private String recipient ;
    private String content ;
    private String date ;

    public Message() {
    }

    public Message(String sender, String recipient, String content, String date) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", content='" + content + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
