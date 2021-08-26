package com.shoppa.Model;

public class ChatModel {

    String mCompanyImg, mCompanyName, mChatMessage;

    public ChatModel(String mCompanyImg, String mCompanyName, String mChatMessage) {
        this.mCompanyImg = mCompanyImg;
        this.mCompanyName = mCompanyName;
        this.mChatMessage = mChatMessage;
    }

    public String getmCompanyImg() {
        return mCompanyImg;
    }

    public void setmCompanyImg(String mCompanyImg) {
        this.mCompanyImg = mCompanyImg;
    }

    public String getmCompanyName() {
        return mCompanyName;
    }

    public void setmCompanyName(String mCompanyName) {
        this.mCompanyName = mCompanyName;
    }

    public String getmChatMessage() {
        return mChatMessage;
    }

    public void setmChatMessage(String mChatMessage) {
        this.mChatMessage = mChatMessage;
    }
}
