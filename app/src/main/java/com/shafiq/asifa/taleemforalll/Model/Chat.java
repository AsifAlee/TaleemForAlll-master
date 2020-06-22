package com.shafiq.asifa.taleemforalll.Model;

public class Chat {

    private String sender;
    private String reciever;
    private String message;
    private boolean isSeen;

    public Chat(String sender, String reciever, String message,boolean isSeen) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
        this.isSeen = isSeen;
    }

    public Chat() {
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(String seen) {
        if (seen.equalsIgnoreCase("true"))
            isSeen = true;
        else
            isSeen = false;
    }
}