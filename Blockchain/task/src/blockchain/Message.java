package blockchain;

import java.io.Serializable;

public class Message implements Serializable {
    private int messageId;
    private String userName;
    private String messageText;

    public Message(String userName, String messageText) {
        this.userName = userName;
        this.messageText = messageText;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageText() {
        return this.messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public int getMessageId() {
        return this.messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Override
    public String toString() {
        return this.userName + ": " + this.messageText;
    }
}
